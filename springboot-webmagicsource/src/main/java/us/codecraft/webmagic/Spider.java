package us.codecraft.webmagic;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;
import us.codecraft.webmagic.thread.CountableThreadPool;
import us.codecraft.webmagic.utils.UrlUtils;
import us.codecraft.webmagic.utils.WMCollections;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Entrance of a crawler.<br>
 * A spider contains four modules: Downloader, Scheduler, PageProcessor and
 * Pipeline.<br>
 * Every module is a field of Spider. <br>
 * The modules are defined in interface. <br>
 * You can customize a spider with various implementations of them. <br>
 * Examples: <br>
 * <br>
 * A simple crawler: <br>
 * Spider.create(new SimplePageProcessor("http://my.oschina.net/",
 * "http://my.oschina.net/*blog/*")).run();<br>
 * <br>
 * Store results to files by FilePipeline: <br>
 * Spider.create(new SimplePageProcessor("http://my.oschina.net/",
 * "http://my.oschina.net/*blog/*")) <br>
 * .pipeline(new FilePipeline("/data/temp/webmagic/")).run(); <br>
 * <br>
 * Use FileCacheQueueScheduler to store urls and cursor in files, so that a
 * Spider can resume the status when shutdown. <br>
 * Spider.create(new SimplePageProcessor("http://my.oschina.net/",
 * "http://my.oschina.net/*blog/*")) <br>
 * .scheduler(new FileCacheQueueScheduler("/data/temp/webmagic/cache/")).run(); <br>
 *
 * @author code4crafter@gmail.com <br>
 * @see Downloader
 * @see Scheduler
 * @see PageProcessor
 * @see Pipeline
 * @since 0.1.0
 */
//实现Runable接口，创建新的线程
//实现Task接口（ 用于确定不同任务的接口），含有getUUID，getSite方法
public class Spider implements Runnable, Task { 
	
	
    protected Downloader downloader;//下载器

    protected List<Pipeline> pipelines = new ArrayList<Pipeline>();//持久层管道

    protected PageProcessor pageProcessor;//页面处理程序

    protected List<Request> startRequests;//初始请求

    protected Site site;//设置

    protected String uuid;//uuid

    protected Scheduler scheduler = new QueueScheduler(); 

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected CountableThreadPool threadPool;//线程池

    protected ExecutorService executorService;

    protected int threadNum = 1;//默认单线程

  //适用于非阻塞算法实现并发控制,AtomicInteger是一个提供原子操作的Integer类，通过线程安全的方式操作加减。
    protected AtomicInteger stat = new AtomicInteger(STAT_INIT);

    //设置exitWhenComplete=false，避免scheduler.poll返回null，且没有工作线程时退出循环。
    //如果exitWhenComplete=false，那么需要开发者手动调用stop()来停止退出爬虫,并调用close()来清理资源。
    protected boolean exitWhenComplete = true; //是否所有url下载完后就退出

    protected final static int STAT_INIT = 0;//爬虫状态，初始化

    protected final static int STAT_RUNNING = 1;//爬虫状态，运行中

    protected final static int STAT_STOPPED = 2;//爬虫状态，停止的

    protected boolean spawnUrl = true;//是否提取子Url，添加新的Request

    protected boolean destroyWhenExit = true;

    private ReentrantLock newUrlLock = new ReentrantLock();

    private Condition newUrlCondition = newUrlLock.newCondition();

    private List<SpiderListener> spiderListeners;

    private final AtomicLong pageCount = new AtomicLong(0);

    //Spider 开始run的时间
    private Date startTime;

    //Spider没有Request的时候，睡眠等待30s
    private int emptySleepTime = 30000;

    /**
     * create a spider with pageProcessor.
     * 静态方法，创建一个爬虫，封装了构造方法
     * @param pageProcessor pageProcessor
     * @return new spider
     * @see PageProcessor
     */
    public static Spider create(PageProcessor pageProcessor) {
        return new Spider(pageProcessor);
    }

    /**
     * create a spider with pageProcessor.
     * 构造方法：创建一个爬虫
     * @param pageProcessor pageProcessor
     */
    public Spider(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        this.site = pageProcessor.getSite();
    }

    /**
     * Set startUrls of Spider.<br>
     * Prior to startUrls of Site.
     * List<String> StartUrls 形式的初始请求>startRequests
     * @param startUrls startUrls
     * @return this
     */
    public Spider startUrls(List<String> startUrls) {
        checkIfRunning();
        this.startRequests = UrlUtils.convertToRequests(startUrls);
        return this;
    }

    /**
     * Set startUrls of Spider.<br>
     * Prior to startUrls of Site.
     * List<Request>startRquests形式的初始请求
     * @param startRequests startRequests
     * @return this
     */
    public Spider startRequest(List<Request> startRequests) {
        checkIfRunning();
        this.startRequests = startRequests;
        return this;
    }

    /**
     * Set an uuid for spider.<br>
     * Default uuid is domain of site.<br>
     *  设置当前Spider的uuid，默认的uuid是Site的域名
     * @param uuid uuid
     * @return this
     */
    public Spider setUUID(String uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * set scheduler for Spider
     * 已过时方法：设置自定义爬虫scheduler
     * @param scheduler scheduler
     * @return this
     * @see #setScheduler(us.codecraft.webmagic.scheduler.Scheduler)
     */
    @Deprecated
    public Spider scheduler(Scheduler scheduler) {
        return setScheduler(scheduler);
    }

    /**
     * set scheduler for Spider
     * 最新方法，设置自定义爬虫scheduler，一个爬虫只能由一个Scheduler
     * @param scheduler scheduler
     * @return this
     * @see Scheduler
     * @since 0.2.1
     */
    public Spider setScheduler(Scheduler scheduler) {
        checkIfRunning();
        Scheduler oldScheduler = this.scheduler;
        this.scheduler = scheduler;
        if (oldScheduler != null) {
            Request request;
            while ((request = oldScheduler.poll(this)) != null) {
                this.scheduler.push(request, this);
            }
        }
        return this;
    }

    /**
     * add a pipeline for Spider
     * 过时方法：自定义pipeline
     * @param pipeline pipeline
     * @return this
     * @see #addPipeline(us.codecraft.webmagic.pipeline.Pipeline)
     * @deprecated
     */
    @Deprecated
    public Spider pipeline(Pipeline pipeline) {
        return addPipeline(pipeline);
    }

    /**
     * add a pipeline for Spider
     *   List<Pipeline>添加自定义pipeline
     * @param pipeline pipeline
     * @return this
     * @see Pipeline
     * @since 0.2.1
     */
    public Spider addPipeline(Pipeline pipeline) {
        checkIfRunning();
        this.pipelines.add(pipeline);
        return this;
    }

    /**
     * set pipelines for Spider
     *  List<Pipeline> 设置自定义pipelines
     * @param pipelines pipelines
     * @return this
     * @see Pipeline
     * @since 0.4.1
     */
    public Spider setPipelines(List<Pipeline> pipelines) {
        checkIfRunning();
        this.pipelines = pipelines;
        return this;
    }

    /**
     * clear the pipelines set
     * 清空 List<Pipeline>pipelines
     * @return this
     */
    public Spider clearPipeline() {
        pipelines = new ArrayList<Pipeline>();
        return this;
    }

    /**
     * set the downloader of spider
     * 过时方法：设置自定义downloader
     * @param downloader downloader
     * @return this
     * @see #setDownloader(us.codecraft.webmagic.downloader.Downloader)
     * @deprecated
     */

	@Deprecated
    public Spider downloader(Downloader downloader) {
        return setDownloader(downloader);
    }

    /**
     * set the downloader of spider
     * 设置自定义downloader
     * @param downloader downloader
     * @return this
     * @see Downloader
     */
    public Spider setDownloader(Downloader downloader) {
        checkIfRunning();
        this.downloader = downloader;
        return this;
    }

    
    /**
     * Spider 线程run的时候，初始化组件
     * 
     * downloader：默认 HttpClientDownloader;
     * pipeline:默认ConsolePipeline;
     * downloader线程cthread:默认1；
     * threadPool线程池初始化
     * startRequests:初始Requests
     * 开启时间记录
     */
    protected void initComponent() {
        if (downloader == null) {
            this.downloader = new HttpClientDownloader();
        }
        if (pipelines.isEmpty()) {
            pipelines.add(new ConsolePipeline());
        }
        downloader.setThread(threadNum);
        if (threadPool == null || threadPool.isShutdown()) {
            if (executorService != null && !executorService.isShutdown()) {
                threadPool = new CountableThreadPool(threadNum, executorService);
            } else {
                threadPool = new CountableThreadPool(threadNum);
            }
        }
        if (startRequests != null) {
            for (Request request : startRequests) {
                addRequest(request);
            }
            startRequests.clear();
        }
        startTime = new Date();
    }

    /*
     * Spider 启动，会阻塞当前Spider线程执行
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        checkRunningStat(); //0.循环检测爬虫状态，直到爬虫运行
        initComponent(); //1.初始化组件
        logger.info("Spider {} started!",getUUID()); //打印 Spider的uuid，默认是域名
        while (!Thread.currentThread().isInterrupted() && stat.get() == STAT_RUNNING) {
        	//Spider线程被中断或者爬虫进入 running状态 退出循环
            final Request request = scheduler.poll(this);//1.从调度器中获取一个Task,返回一个不可更改的Request
            if (request == null) {//如果获取不到Request ,退出此次循环
                if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                    break;
                }
             
                // wait until new url added
                waitNewUrl();//等待一段时间
            } else {
                threadPool.execute(new Runnable() {//如果获取到Request,开启线程处理Downler处理Request
                    @Override
                    public void run() {
                        try {
                            processRequest(request); //下载yem
                            onSuccess(request); //下载成功后，
                        } catch (Exception e) {
                            onError(request);
                            logger.error("process request " + request + " error", e);
                        } finally {
                        	  
                            pageCount.incrementAndGet();
                            signalNewUrl();
                        }
                    }
                });
            }
        }
        stat.set(STAT_STOPPED);
        // release some resources
        if (destroyWhenExit) {
            close();
        }
        logger.info("Spider {} closed! {} pages downloaded."
        		+ "startTime:{},entTime:{}", getUUID(), pageCount.get(),startTime,new Date());
    }

    protected void onError(Request request) {
        if (CollectionUtils.isNotEmpty(spiderListeners)) {
            for (SpiderListener spiderListener : spiderListeners) {
                spiderListener.onError(request);
            }
        }
    }

    
    /*
     * Spider 监听者
     */
    protected void onSuccess(Request request) {
        if (CollectionUtils.isNotEmpty(spiderListeners)) {
            for (SpiderListener spiderListener : spiderListeners) {
                spiderListener.onSuccess(request);
            }
        }
    }

    
    /*
     * 检查运行状态 （已经运行的爬虫，再次执行运行抛异常）
     * 获取当前爬虫状态，一直循环，直到爬虫进入运行状态为止 
     */
    private void checkRunningStat() {
        while (true) {
            int statNow = stat.get();//获取爬虫状态
            if (statNow == STAT_RUNNING) {
                throw new IllegalStateException("Spider is already running!");
            }
            if (stat.compareAndSet(statNow, STAT_RUNNING)) {
                break;
            }
        }
    }

    public void close() {
        destroyEach(downloader);
        destroyEach(pageProcessor);
        destroyEach(scheduler);
        for (Pipeline pipeline : pipelines) {
            destroyEach(pipeline);
        }
        threadPool.shutdown();
    }

    private void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Process specific urls without url discovering.
     * 抓取测试页面
     * @param urls urls to process
     */
    public void test(String... urls) {
        initComponent();
        if (urls.length > 0) {
            for (String url : urls) {
                processRequest(new Request(url));
            }
        }
    }

    
    /**
     * downloader处理Request，下载Page
     * 
     * @param request
     */
    private void processRequest(Request request) {
        Page page = downloader.download(request, this); //下载页面 download(Request,Task)
        if (page.isDownloadSuccess()){//页面下载成功后
            onDownloadSuccess(request, page);
        } else {
            onDownloaderFail(request);
        }
    }

    /*
     * 成功下载Page后的处理：
     * 	打印http 响应码，默认只接受200，算下载成功
     * 	提取子url
     *  根据ResultItems 是否skip，进行持久化
     */
    private void onDownloadSuccess(Request request, Page page) {
    	logger.info("onDownloadSuccess page.getStatusCode() "+page.getStatusCode());//http 响应码
      
    	if (site.getAcceptStatCode().contains(page.getStatusCode())){ 
    		logger.info("onDownloadSuccess enter pageProcessor.process ");
            pageProcessor.process(page);
            logger.info("onDownloadSuccess out pageProcessor.process ");
	      	
            extractAndAddRequests(page, spawnUrl); //提取 子url
            if (!page.getResultItems().isSkip()) {
                for (Pipeline pipeline : pipelines) {
                    pipeline.process(page.getResultItems(), this);
                }
            }
        } else {
            logger.info("page status code error, page {} , code: {}", request.getUrl(), page.getStatusCode());
        }
        sleep(site.getSleepTime());
        return;
    }

    /*
     * 下载Page失败，根据Site里面默认重复下次次数，进行重复下载
     *  中间睡眠site里面间隔时间，目前默认5s
     */
    private void onDownloaderFail(Request request) {
        if (site.getCycleRetryTimes() == 0) {//默认重复次数为0
            sleep(site.getSleepTime());
        } else {
            // for cycle retry
            doCycleRetry(request);//重新克隆Request加入队列
        }
    }

    /*
     * 重新克隆Request加入队列
     */
    private void doCycleRetry(Request request) {
        Object cycleTriedTimesObject = request.getExtra(Request.CYCLE_TRIED_TIMES);
        //判断CYCLE_TRIED_TIMES 是否为null，不为null，循环重试次数+1，为null，重试1次
        if (cycleTriedTimesObject == null) {
            addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, 1));
        } else {
            int cycleTriedTimes = (Integer) cycleTriedTimesObject;
         
            cycleTriedTimes++;
            if (cycleTriedTimes < site.getCycleRetryTimes()) {
            	//判断是否超过最大允许值,重新加入队列的次数最大不能超过  site里面设置的cycleRetryTimes
                addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, cycleTriedTimes));
            }
        }
        sleep(site.getRetrySleepTime());
    }

    protected void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error("Thread interrupted when sleep",e);
        }
    }

    /*
     * 从下载的Page中获取子url，添加Request
     */
    protected void extractAndAddRequests(Page page, boolean spawnUrl) {
        if (spawnUrl && CollectionUtils.isNotEmpty(page.getTargetRequests())) {
            for (Request request : page.getTargetRequests()) {
                addRequest(request);
            }
        }
    }

    /*
     * push添加Request到Scheduler里面
     */
    private void addRequest(Request request) {
        if (site.getDomain() == null && request != null && request.getUrl() != null) {
            site.setDomain(UrlUtils.getDomain(request.getUrl())); //通过Url获取设置域名
        }
        scheduler.push(request, this);
    }

    /*
     * 检测Spider运行状态，已运行则抛异常
     */
    protected void checkIfRunning() {
        if (stat.get() == STAT_RUNNING) {
            throw new IllegalStateException("Spider is already running!");
        }
    }

    /*
     * 用户线程异步执行，异步启动，当前线程继续执行
     */
    public void runAsync() {
        Thread thread = new Thread(this);
        thread.setDaemon(false); //用户线程
        thread.start();
    }

    /**
     * Add urls to crawl. <br>
     *
     * @param urls urls
     * @return this
     */
    public Spider addUrl(String... urls) {
        for (String url : urls) {
            addRequest(new Request(url));
        }
        signalNewUrl();
        return this;
    }

    /**
     * Download urls synchronizing.
     *
     * @param urls urls
     * @param <T> type of process result
     * @return list downloaded
     */
    public <T> List<T> getAll(Collection<String> urls) {
        destroyWhenExit = false;
        spawnUrl = false;
        if (startRequests!=null){
            startRequests.clear();
        }
        for (Request request : UrlUtils.convertToRequests(urls)) {
            addRequest(request);
        }
        CollectorPipeline collectorPipeline = getCollectorPipeline();
        pipelines.add(collectorPipeline);
        run();
        spawnUrl = true;
        destroyWhenExit = true;
        return collectorPipeline.getCollected();
    }

    protected CollectorPipeline getCollectorPipeline() {
        return new ResultItemsCollectorPipeline();
    }

    public <T> T get(String url) {
        List<String> urls = WMCollections.newArrayList(url);
        List<T> resultItemses = getAll(urls);
        if (resultItemses != null && resultItemses.size() > 0) {
            return resultItemses.get(0);
        } else {
            return null;
        }
    }

    /**
     * Add urls with information to crawl.<br>
     *
     * @param requests requests
     * @return this
     */
    public Spider addRequest(Request... requests) {
        for (Request request : requests) {
            addRequest(request);
        }
        signalNewUrl();
        return this;
    }

    private void waitNewUrl() {
        newUrlLock.lock();
        try {
            //double check
            if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                return;
            }
            newUrlCondition.await(emptySleepTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.warn("waitNewUrl - interrupted, error {}", e);
        } finally {
            newUrlLock.unlock();
        }
    }

    private void signalNewUrl() {
        try {
            newUrlLock.lock();
            newUrlCondition.signalAll();
        } finally {
            newUrlLock.unlock();
        }
    }

	/*
	*异步启动，当前线程继续执行
	*/
    public void start() {
        runAsync();
    }

    /*
     * 停止爬虫
     */
    public void stop() {
        if (stat.compareAndSet(STAT_RUNNING, STAT_STOPPED)) {
            logger.info("Spider " + getUUID() + " stop success!");
        } else {
            logger.info("Spider " + getUUID() + " stop fail!");
        }
    }

    /**
     * start with more than one threads
     *
     * @param threadNum threadNum
     * @return this
     */
    public Spider thread(int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
        return this;
    }

    /**
     * start with more than one threads
     *
     * @param executorService executorService to run the spider
     * @param threadNum threadNum
     * @return this
     */
    public Spider thread(ExecutorService executorService, int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
        this.executorService = executorService;
        return this;
    }

    public boolean isExitWhenComplete() {
        return exitWhenComplete;
    }

    /**
     * Exit when complete. <br>
     * True: exit when all url of the site is downloaded. <br>
     * False: not exit until call stop() manually.<br>
     *
     * @param exitWhenComplete exitWhenComplete
     * @return this
     */
    public Spider setExitWhenComplete(boolean exitWhenComplete) {
        this.exitWhenComplete = exitWhenComplete;
        return this;
    }

    public boolean isSpawnUrl() {
        return spawnUrl;
    }

    /**
     * Get page count downloaded by spider.
     *
     * @return total downloaded page count
     * @since 0.4.1
     */
    public long getPageCount() {
        return pageCount.get();
    }

    /**
     * Get running status by spider.
     *
     * @return running status
     * @see Status
     * @since 0.4.1
     */
    public Status getStatus() {
        return Status.fromValue(stat.get());
    }


    public enum Status {
        Init(0), Running(1), Stopped(2);

        private Status(int value) {
            this.value = value;
        }

        private int value;

        int getValue() {
            return value;
        }

        public static Status fromValue(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            //default value
            return Init;
        }
    }

    /**
     * Get thread count which is running
     *
     * @return thread count which is running
     * @since 0.4.1
     */
    public int getThreadAlive() {
        if (threadPool == null) {
            return 0;
        }
        return threadPool.getThreadAlive();
    }

    /**
     * Whether add urls extracted to download.<br>
     * Add urls to download when it is true, and just download seed urls when it is false. <br>
     * DO NOT set it unless you know what it means!
     *
     * @param spawnUrl spawnUrl
     * @return this
     * @since 0.4.0
     */
    public Spider setSpawnUrl(boolean spawnUrl) {
        this.spawnUrl = spawnUrl;
        return this;
    }

    
    /*
     * 获取Spider的uuid，默认是域名 ，如果Site是空，返回一个随机字符串
     */
    @Override
    public String getUUID() {
        if (uuid != null) {
            return uuid;
        }
        if (site != null) {
            return site.getDomain();
        }
        uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public Spider setExecutorService(ExecutorService executorService) {
        checkIfRunning();
        this.executorService = executorService;
        return this;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public List<SpiderListener> getSpiderListeners() {
        return spiderListeners;
    }

    public Spider setSpiderListeners(List<SpiderListener> spiderListeners) {
        this.spiderListeners = spiderListeners;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * Set wait time when no url is polled.<br><br>
     *
     * @param emptySleepTime In MILLISECONDS.
     */
    public void setEmptySleepTime(int emptySleepTime) {
        this.emptySleepTime = emptySleepTime;
    }
}

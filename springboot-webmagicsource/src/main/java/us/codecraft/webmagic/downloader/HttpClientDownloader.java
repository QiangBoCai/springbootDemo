package us.codecraft.webmagic.downloader;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.CharsetUtils;
import us.codecraft.webmagic.utils.HttpClientUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


/**
 * The http downloader based on HttpClient.
 *
 *实现类：HttpClientDownloader。负责通过HttpClient下载页面
 */
public class HttpClientDownloader extends AbstractDownloader {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    //httpclients Map集合，Map 的key是Site的domain域名，以便重用
    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();
    //负责生成httpclient实例
    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();
    //负责生成httpClientRequestContext 实例
    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
    
    //IP代理提供
    private ProxyProvider proxyProvider;
    //是否在响应的Page对象中设置Header，默认true
    private boolean responseHeader = true;

    public void setHttpUriRequestConverter(HttpUriRequestConverter httpUriRequestConverter) {
        this.httpUriRequestConverter = httpUriRequestConverter;
    }

    
    
    /**
     * 设置IP代理提供者，由proxyprovider 加载提供IP 代理
     * @param proxyProvider
     */
    public void setProxyProvider(ProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }
    
    
    /**
     *  根据Site 获取httpClient 实例,每个httpClient 
     *  与 Site的域名是一一对应的，组成了一个httpClients的Map集合
     * @param site
     * @return httpClient
     */
    private CloseableHttpClient getHttpClient(Site site) {
    	//注意为了确保线程安全性，这里用到了线程安全的双重判断机制。
        if (site == null) {
            return httpClientGenerator.getClient(null);
        }
        //通过Site获取域名，然后通过域名判断是否在httpClients这个map中已存在HttpClient实例，如果存在则重用
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
       //没有域名对应的httpclient则新建一个，并存放到httpClients 集合中，以便重用
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }

    
    /**
     *实现了Downloader接口的download方法
     *
     *传入一个从scheduler中取出的Request以及当前的Spider(实现了Task接口，由getSite方法)
     *
     *返回一个Page
     */
    
    @Override
    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        //初始化HttpClient  response
        //1.根据Task getSite，   初始化httpClient
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient(task.getSite());
        
        //2.ProxyProvider 获取IP代理
        Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        
        //3.更具Request,Site,Proxy  来实例化httpClientRequestContext ，进行配置信息设置
        
        logger.debug("httpClientRequestContext init start");
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
          
        logger.debug("httpClientRequestContext init end");
        Page page = Page.fail();
        try {
        	//4.httpClient执行，返回HttpResponse
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
            //5.下载的内容httpResponse转换为Page对象
            page = handleResponse(request, request.getCharset() != null ? request.getCharset() : task.getSite().getCharset(), httpResponse, task);
            //6.1 下载成功后的处理
            onSuccess(request);
            logger.info("downloading page success {}", request.getUrl());
            return page;
        } catch (IOException e) {
            logger.warn("download page {} error", request.getUrl(), e);
           //6.2 下载失败后的处理
            onError(request);
            return page;
        } finally {
            if (httpResponse != null) {
            	//7.1 释放资源
                //ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
            //7.2 回收IP代理
            if (proxyProvider != null && proxy != null) {
                proxyProvider.returnProxy(proxy, page, task);
            }
        }
    }

    /**
     * 设置下载的线程数
     */
    @Override
    public void setThread(int thread) {
        httpClientGenerator.setPoolSize(thread);
    }

    
   /**
    * 处理相应信息，返回Page对象
    * 
    * */
    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
    	//1.1 以byte数组的形式获取httpResponse 内容
    	byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        //1.2 获取Response 的contentType
        String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue();
        //2. 根据httpResponse 的Byte数组  初始化Page实例 ，
        Page page = new Page();
        page.setBytes(bytes);

        
        //3.是否将响应消息解析成格式化正文 RawText，默认 要
        if (!request.isBinaryContent()){
            if (charset == null) {
                charset = getHtmlCharset(contentType, bytes);
            }
            //根据CharSet 将响应内容解析成RawText，即带charset的String
            page.setCharset(charset);
            page.setRawText(new String(bytes, charset));
            logger.debug("handleResponse rawText:"+new String(bytes,charset));
        }
        //根据Request的String形式的Url，设置Page的Selectable形式的Url  
        page.setUrl(new PlainText(request.getUrl()));
        
    //    logger.debug("handleResponse PlainText:"+page.getUrl()+";request Url:"+request.getUrl());
        
        page.setRequest(request);
        //设置响应对象Page的 statusCode
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        //设置Page 的DownloadSuccess 为true
        page.setDownloadSuccess(true);
        
        //设置Page的Header
        if (responseHeader) {
            page.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
        }
        return page;
    }

    
    /**
     * 获取Html页面的编码
     * 
     * @param contentType
     * @param contentBytes
     * @return
     * @throws IOException
     */
    private String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {
        String charset = CharsetUtils.detectCharset(contentType, contentBytes);
        if (charset == null) {
            charset = Charset.defaultCharset().name();
            logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
        }
        return charset;
    }
}

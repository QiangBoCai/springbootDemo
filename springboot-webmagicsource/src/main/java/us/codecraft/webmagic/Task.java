package us.codecraft.webmagic;

/**
 * Interface for identifying different tasks.<br>
 * 用于确定不同任务的接口
 * 可以获取Spider的uuid，site
 * @since 0.1.0
 * @see us.codecraft.webmagic.scheduler.Scheduler
 * @see us.codecraft.webmagic.pipeline.Pipeline
 */
public interface Task {

    /**
     * unique id for a task. 一项任务的唯一Id
     *
     * @return uuid
     */
    public String getUUID();

    /**
     * site of a task 一项任务的设置
     *
     * @return site
     */
    public Site getSite();

}

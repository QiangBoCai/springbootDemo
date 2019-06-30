package us.codecraft.webmagic.proxy;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;

/**
 * Proxy生产者接口
 * 
 * 两个方法：
 * ①
 *  
 */
public interface ProxyProvider {

    /**
     *完成下载后，将IP代理返回给提供程序。
     * Return proxy to Provider when complete a download.
     * @param proxy the proxy config contains host,port and identify info
     * @param page the download result
     * @param task the download task
     */
    void returnProxy(Proxy proxy, Page page, Task task);

    /**
     * 通过一些策略获取IP 代理
     * Get a proxy for task by some strategy.
     * @param task the download task
     * @return proxy 
     */
    Proxy getProxy(Task task);
    
}

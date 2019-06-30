package com.lance.selectable.xpath;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/***
 * 验证xpth的正确性
 * 发现http和https网站是有区别的：
 * https有可能根据xpath获取到的内容为null 原因是
 * ①xpath书写错误:可以借助firefox浏览器的firebug，firefinder，firepath 插件工具验证
 * ② element.data()的结果是这个东东:
		location.replace(location.href.replace("https://","http://"));
	解决办法是我们把startUrl的https改为http
 * ③ element.html()返回 的内容错误或者不完整导致element获取节点信息null或者错误
 * xpath的内容以element.html()为准 
 * eg:    String temps = page.getHtml().xpath("//a/text()").nodes().toString();
	
 * eg:{"code":1,"msg":{"400":"Bad Request"}}
 */
public class PageProcesssorXpathDemo implements PageProcessor{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String START_URL = "https://mart.coding.net/project/10452";
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
	@Override
	public void process(Page page) {	//保存抽取结果为一个JavaBean
		
	    String temps = page.getHtml().xpath("//a/text()").nodes().toString();

		logger.debug("temps"+temps);
	    
	    logger.debug("currentUrl:"+page.getRequest().getUrl());
	}

	@Override
	public Site getSite() {
		return site;
	}

	
	public static void main(String[] args) 
	{			//创建爬虫
		Spider.create(new PageProcesssorXpathDemo())
		 	   //从这里开始抓取
		 	   .addUrl(START_URL)
		 	   //启动爬虫
		 	   .run();
		
	}
}

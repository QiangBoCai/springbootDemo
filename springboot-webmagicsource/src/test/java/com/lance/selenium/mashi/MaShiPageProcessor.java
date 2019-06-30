package com.lance.selenium.mashi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lance.webmagic.downloader.selenium.SeleniumDownloader;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/***
 * 爬虫功能：
 * 爬取码市上爬虫相关项目，过滤出可以接的单，实现定期，增量爬取
 *
 */
public class MaShiPageProcessor implements PageProcessor{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
	@Override
	public void process(Page page) {	//保存抽取结果为一个JavaBean
		
	    String temps = page.getHtml().xpath("//a/text()").toString();
	    ItemInfo itemInfo = new ItemInfo();
	    itemInfo.setItemName(temps);
	    page.putField("item", itemInfo);
	    logger.debug("temps"+temps);
	    logger.debug(" enter MaShiPageProcessor + currentUrl:"+page.getRequest().getUrl());
	}

	@Override
	public Site getSite() {
		return site;
	}

	
	public static void main(String[] args) 
	{			//创建爬虫
		Spider.create(new MaShiPageProcessor())
		 	   //从这里开始抓取
		 	   .addUrl("https://mart.coding.net/projects")
		 	   //保存结果
		 	   .setDownloader(new SeleniumDownloader("D:/tools/chromedriver.exe"))
		 	   .addPipeline(new MaShiPipeline())
		 	   //开启5个线程抓取
		 	   .thread(2)
		 	   //启动爬虫
		 	   .run();
		
	}
}

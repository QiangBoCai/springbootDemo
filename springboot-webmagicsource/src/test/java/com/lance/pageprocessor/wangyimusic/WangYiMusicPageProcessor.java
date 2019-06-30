package com.lance.pageprocessor.wangyimusic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lance.webmagic.downloader.selenium.SeleniumDownloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/***********************
 * @author Lance	    
 * @version 1.0        
 * @created 2017-11-16    
 ***********************/
public class WangYiMusicPageProcessor implements PageProcessor {

		 protected Logger logger = LoggerFactory.getLogger(getClass());
		//1.爬虫相关配置，eg:编码，抓取间隔，重试次数等
		private Site site = Site.me()
							.setRetryTimes(3)
							.setSleepTime(1000)
							.setTimeOut(10000)
							.addHeader("Accept-Encoding", "gzip, deflate");
		
		@Override
		public Site getSite() 
		{
			return site;
		}

		@Override
		//process 是定制爬虫逻辑的核心接口，里面编写抽取逻辑
		public void process(Page page) 
		{
		    logger.debug("xiaotao:"+page.toString().contains("lyric-content"));

		    logger.debug("xiaotao:"+page.getHtml().xpath("//*[@id='lyric-content']"));
		}

		
		public static void main(String[] args) 
		{			//创建爬虫
			Spider.create(new WangYiMusicPageProcessor())
			 	   //从这里开始抓取
			 	   .addUrl("http://music.163.com/song?id=189688")
			 	   .setDownloader(new SeleniumDownloader("D:/tools/chromedriver.exe"))
			 	   //启动爬虫
			 	   .run();
			
		}
}

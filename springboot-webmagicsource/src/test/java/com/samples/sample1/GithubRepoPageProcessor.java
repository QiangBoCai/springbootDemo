package com.samples.sample1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author Lance
 * 第一个爬虫GithubRepoPageProcessor
 * 了解weblogic爬虫创建的基本流程;
 *	
 * 0.爬虫相关配置Site，编码，抓取间隔，重试次数等
 * 1.Spider  create创建爬虫，thread开启线程，run启动爬虫
 * 2.页面元素抽取Page>Resultitems>JavaBean
 * 3.新链接Page抽取>addTargetRequests
 * 4.addPipeline 信息存储
 */
public class GithubRepoPageProcessor implements PageProcessor{
	 protected Logger logger = LoggerFactory.getLogger(getClass());
	
	 //0.爬虫相关配置，eg:编码，抓取间隔，重试次数等
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
	//process 是定制爬虫逻辑的核心接口，里面编写页面抽取逻辑
	public void process(Page page) 
	{
  		
		//2.页面元素抽取：定义如何抽取页面信息,得到抽取结果Resultitems,甚至一个JavaBean
		
	 /*
	  	page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString()); //getUrl获取当前页的符合正则表达式的url，
	    page.putField("name", page.getHtml().xpath("//h1[@class='public']/strong/a/text()").toString());
	    if (page.getResultItems().get("name")==null)
	    {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
	 */
		//保存抽取结果为一个JavaBean
	    GithubRepo githubRepo = new GithubRepo();
	    githubRepo.setAuthor(page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
	    githubRepo.setName(page.getHtml().xpath("//h1[@class='public']/strong/a/text()").toString());
	    githubRepo.setReadme(page.getHtml().xpath("//div[@id='readme']/tidyText()").toString());
	    githubRepo.setUrl(page.getRequest().getUrl());
	    if (githubRepo.getName() == null) 
	    {
	    	
	        //skip this page
	        page.setSkip(true);
	    } else {
	        page.putField("repo", githubRepo);
	        
	    }
		//3.链接发现：从当前页面获取新的ＵＲＬ
  		page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
  		page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
  		
	}

	
	public static void main(String[] args) 
	{			//1.创建爬虫
		Spider.create(new GithubRepoPageProcessor())
		 	   .addUrl("https://github.com/code4craft")
		 	   //4.保存结果
		 	   .addPipeline(new JsonFilePipeline("./data/"))
		 	   //开启5个线程抓取
		 	   .thread(5)
		 	   //启动爬虫
		 	   .run();
		
	}
}

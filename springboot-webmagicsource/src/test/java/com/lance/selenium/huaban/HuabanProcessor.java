package com.lance.selenium.huaban;

import com.lance.webmagic.downloader.selenium.SeleniumDownloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 花瓣网http://huaban.com/
 * 花瓣网抽取器
 * 使用Selenium做页面动态渲染。
 */
public class HuabanProcessor implements PageProcessor {

    private Site site;

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://huaban\\.com/.*").all());
        if (page.getUrl().toString().contains("pins")) {
            page.putField("img", page.getHtml().xpath("//div[@class='image-holder']/a/img/@src").toString());
        } else {
            page.getResultItems().setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        if (null == site) {
            site = Site.me().setDomain("huaban.com").setSleepTime(0);
        }
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new HuabanProcessor()).thread(2)
             //   .addPipeline(new FilePipeline("D\\:test"))
                .setDownloader(new SeleniumDownloader("D:/tools/chromedriver.exe"))
                .addUrl("http://huaban.com/")
                .runAsync();
    }
}

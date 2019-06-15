package com.cas.twitter.crawler;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cas.twitter.crawler.util.DateUtil;
import com.cas.twitter.crawler.util.MyProperties;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;


@Component
public class TwitterTaskScheduled {
	
    private static final Logger log = LoggerFactory.getLogger(TwitterTaskScheduled.class);
	
    @Autowired
	MyProperties properties;
    
	@Async
    @Scheduled(cron = "0 0/3 * * * *")
    public void scheduled1() throws InterruptedException {
		//cron表达式设置定时任务时间
		String[] list = null;
		try {
			 //configure twitter username
			list = properties.getUser().split(",");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list == null) return;
		String startday = DateUtil.getCurrentDayBefore(-2);//前一天
		String endday = DateUtil.getCurrentDayBefore(0);
		Spider spider = new Spider(new TwitterPageProcessor());
		for (String account : list) {
			String url = "https://twitter.com/i/search/timeline?l=&f=tweets&q=%27from%3A"+account+"%20since%3A"+startday+"%20until%3A"+endday+"%27&src=typed&max_position=";
			spider.addUrl(url);
			System.out.println(url);
		}
		spider.getUUID();
		spider
		.addPipeline(new TwitterPipeline())
		.setScheduler(
						new QueueScheduler()
						.setDuplicateRemover(new HashSetDuplicateRemover()))
				.thread(3);
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		//本机代理，端口需要看shadowsocks的配置文件，每次重新连接翻墙，需要更改此端口
		int proxy_port = Integer.parseInt(properties.getListenport());
	    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1",proxy_port,"","")));
	    spider.setDownloader(httpClientDownloader);
	    //Moniter moniter = new Moniter(spider);
		//moniter.start();
		spider.run();
	
	
    }

}

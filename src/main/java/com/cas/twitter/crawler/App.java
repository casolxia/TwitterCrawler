package com.cas.twitter.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import com.cas.twitter.crawler.util.DateUtil;
import com.cas.twitter.crawler.util.MyProperties;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;


public class App implements CommandLineRunner{

	@Autowired
	MyProperties properties;
	  
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	public void run(String... args) throws Exception {
		String[] list = null;//username list
		try {
			list = properties.getUser().split(",");//获取配置要抓取的用户名数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list == null) return;
		String startday = DateUtil.getCurrentDayBefore(-2);//查询时间范围
		String endday = DateUtil.getCurrentDayBefore(0);
		Spider spider = new Spider(new TwitterPageProcessor());
		for (String account : list) {
			String url = "https://twitter.com/i/search/timeline?l=&f=tweets&q=%27from%3A"+account+"%20since%3A"+startday+"%20until%3A"+endday+"%27&src=typed&max_position=";
			spider.addUrl(url);
		}
		spider.getUUID();
		spider
		.addPipeline(new TwitterPipeline())
		.setScheduler(
						new QueueScheduler()
						.setDuplicateRemover(new HashSetDuplicateRemover()))
				.thread(3);
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		//本机代理，端口需要看shadowsocks的配置文件，翻墙代理本机端口
		int proxy_port = Integer.parseInt(properties.getListenport());
	    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1",proxy_port,"","")));
	    spider.setDownloader(httpClientDownloader);
		spider.run();
    
	}
}

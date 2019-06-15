package com.cas.twitter.crawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;

public class Moniter extends Thread {

	private static Logger logger = LoggerFactory.getLogger(Moniter.class);
	
	private Spider spider;
	
	public Moniter(Spider spider){
		this.spider = spider;
	}
	//Init(0), Running(1), Stopped(2);
	public void run(){
		while(true){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(spider.getStatus().toString()=="Stopped"){
				//System.out.println("spider结束："+spider.getStatus());
				break;
			}
		}
	}
}

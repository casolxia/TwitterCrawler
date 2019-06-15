package com.cas.twitter.crawler.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:twi.properties")
@ConfigurationProperties(prefix = "twi")
public class MyProperties {

	private String listenport;

	private String user;

	public String getListenport() {
		return listenport;
	}

	public void setListenport(String listenport) {
		this.listenport = listenport;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}

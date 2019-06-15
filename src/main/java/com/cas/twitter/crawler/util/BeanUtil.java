package com.cas.twitter.crawler.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * spring bean util
 * @author Administrator
 *
 */
@Service
public class BeanUtil implements ApplicationContextAware{

	private static ApplicationContext ctx;

    public static Object getBean(String id) {
        if (ctx == null) {
            throw new NullPointerException("ApplicationContext is null");
        }
        return ctx.getBean(id);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        ctx = applicationContext;
    }

}

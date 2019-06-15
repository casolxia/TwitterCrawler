package com.cas.twitter.crawler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cas.twitter.crawler.util.MyProperties;
import com.cas.twitter.crawler.vo.TwiImgVO;
import com.cas.twitter.crawler.vo.TwiUser;
import com.cas.twitter.crawler.vo.TwitterVO;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

@Service
public class TwitterPageProcessor implements PageProcessor {

	@Autowired
	MyProperties properties;
	
	private Site site = Site
			.me()
			.setCycleRetryTimes(5)
			.setRetryTimes(5)
			.setSleepTime(500)
			.setTimeOut(3 * 60 * 1000)
			.setUserAgent(
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
			.addHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
			.setCharset("utf-8");

	private static Logger logger = LoggerFactory.getLogger(TwitterPageProcessor.class);
	
	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		String html = page.getRawText();
		JSONObject jsonObject = null;
		try{
         jsonObject = JSONObject.parseObject(html);
		}catch(Exception e){
			logger.info("---------twittertext---json----error-------");
			logger.info("data:"+html);
			logger.info("---------twittertext---json----error-------");
		}
		if(jsonObject==null)return;
		String items_html = (String) jsonObject.get("items_html");
		org.jsoup.nodes.Document itemsDoc = Jsoup.parse(items_html);
		
		String  min_position = (String) jsonObject.get("min_position");
        if(min_position!=null&&!min_position.equalsIgnoreCase("")) {
        	int index = page.getUrl().toString().indexOf("max_position=");
        	String query = page.getUrl().toString().substring(0, index+13);
    		String next = query+min_position;
    		page.addTargetRequest(next);
        }
		
		Elements items = itemsDoc.select("li[data-item-type=tweet] div");
		List<TwitterVO> list = new ArrayList<TwitterVO>();
        List<TwiImgVO> imgList = new ArrayList<TwiImgVO>();
        List<TwiUser> userList = new ArrayList<TwiUser>();
		for(Element el : items){
            String usernameTweet = el.select("span[class=username u-dir u-textTruncate] b").text();
            String id = el.attr("data-tweet-id");
            if(id==null||id.equalsIgnoreCase(""))
            	continue;
            System.out.println(usernameTweet);
            String reply_to = "";
            if(usernameTweet.split(" ").length>1){
            	String[]  users = usernameTweet.split(" ");
            	for(int j = 1 ; j <users.length ; j++ ){
            		reply_to += users[j] +" ";
            	} 
                System.out.println("reply_to:"+reply_to);//评论回复
            	usernameTweet = users[0];
            }
            
			String twitext = el.select("div[class=js-tweet-text-container] p").text().replace(" # ", "#").replace(" @ ", "@");
            if(twitext.equalsIgnoreCase("")) 
            	continue;
            
            TwitterVO vo = new TwitterVO();
            vo.setUserName(usernameTweet);
            vo.setText(twitext);
            vo.setId(id);
            vo.setIsReply(reply_to);
            String twiurl = el.attr("data-permalink-path");
            vo.setUrl(twiurl);
            String num_ret = el.select(".ProfileTweet-action--retweet .ProfileTweet-actionCount").attr("data-tweet-stat-count");        
            vo.setRetweet(num_ret);
            String num_fav = el.select(".ProfileTweet-action--favorite .ProfileTweet-actionCount").attr("data-tweet-stat-count");
            vo.setFavorite(num_fav);

            String num_reply = el.select(".ProfileTweet-action--reply .ProfileTweet-actionCount").attr("data-tweet-stat-count");
            vo.setReply(num_reply);
            String datetime = el.select("div[class=stream-item-header] small[class=time] a span").attr("data-time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
            Date date = new Date(Long.parseLong(datetime)*1000L);      
            String twi_date = simpleDateFormat.format(date);        
            vo.setDatetime(twi_date);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String now = simpleDateFormat.format(new Date());
            vo.setCrawlertime(now);
            Elements imagesEl = el.select(".js-adaptive-photo");
            for(Element elImg:imagesEl){
            	//imgList.add(elImg.attr("data-image-url"));
            	TwiImgVO imgVO = new TwiImgVO();
            	imgVO.setId(id);
            	imgVO.setName(usernameTweet);
            	imgVO.setUrl(elImg.attr("data-image-url"));
                imgList.add(imgVO);
            }
            
            String photos= el.attr("data-card-type");
            if(photos!=null&&!photos.equalsIgnoreCase("")) {}  
            
            TwiUser user = new TwiUser();
            String user_id = el.attr("data-user-id");

            String name = el.attr("data-name");
            String screen_name = el.attr("data-screen-name");

            String avatar = el.select("div[class=content] div[class=stream-item-header] a img").attr("src");
            user.setAvatar(avatar);
            user.setId(user_id);
            user.setName(name);
            user.setScreenName(screen_name);
            userList.add(user);
            list.add(vo);
		}
		page.putField("twitter", list);//twitter data
		page.putField("twitterimg", imgList);//twitter images
		page.putField("twitteruser", userList);//twitter user
		logger.info("page:"+page.getUrl());
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/conf/applicationContext.xml");
    	ctx.registerShutdownHook();
    	//https://twitter.com/i/search/timeline?l=&f=tweets&q='from:WhiteHouse since:2019-09-23 until:2018-09-30'&src=typed&max_position=
		String url = "https://twitter.com/i/search/timeline?l=&f=tweets&q=%27from%3AWhiteHouse%20since%3A2018-09-27%20until%3A2018-10-02%27&src=typed&max_position=";
		Spider spider = new Spider(new TwitterPageProcessor());
		spider.addUrl(url)
		.setScheduler(
				new QueueScheduler()
						.setDuplicateRemover(new HashSetDuplicateRemover()))
						.addPipeline(new TwitterPipeline())
		.thread(2);
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		int proxy_port = Integer.parseInt("2345");//your proxy port
	    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1",proxy_port,"","")));
	    spider.setDownloader(httpClientDownloader);
		spider.run();
	}
}

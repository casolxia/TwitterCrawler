package com.cas.twitter.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cas.twitter.crawler.dao.TwiMapper;
import com.cas.twitter.crawler.vo.TwiImgVO;
import com.cas.twitter.crawler.vo.TwiUser;
import com.cas.twitter.crawler.vo.TwitterVO;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

@Service
public class TwitterPipeline extends FilePipeline {
	
	public static final int cache = 10 * 1024;

	@Autowired
	private TwiMapper dao;
	    
	@Override
	public void process(ResultItems resultItems, Task arg1) {
		List<TwitterVO> list = resultItems.get("twitter");// twitter text
		List<TwiImgVO> imgList = resultItems.get("twitterimg");// twitter images
		List<TwiUser> userList = resultItems.get("twitteruser");// twitter user
		if (list == null)
			return;
		for (int i = 0; i < list.size(); i++) {
			TwitterVO vo = list.get(i);
			try {
				dao.SaveTwi(vo);//save twiter data to db
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (imgList == null)
			return;
		for (int i = 0; i < imgList.size(); i++) {
			//save image data
		}
		if (userList == null)
			return;
		for (int i = 0; i < userList.size(); i++) {
			//save user data
		}

	}
	
	
	
	public void download(String url, String filepath, String filename)
			throws Exception {
		//filename = System.currentTimeMillis() + "";
		InputStream is = null;
		FileOutputStream fileout = null;
		try {
			File fp = new File(filepath);
			// 创建目录
			if (!fp.exists()) {
				fp.mkdirs();// 目录不存在的情况下，创建目录。
			}
			// 从连接池获取连接
	        HttpHost proxy = new HttpHost("127.0.0.1", 59073, "http");//代理
			//HttpClient httpclient = HttpClient452Utils.getHttpClient();
	      //把代理设置到请求配置
	        RequestConfig defaultRequestConfig = RequestConfig.custom()
	                .setProxy(proxy)
	                .build();

	        //实例化CloseableHttpClient对象
	        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			
			// 创建文件
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			File file = new File(filepath + "/" + filename);
			if (!file.exists()) {
				file.createNewFile();
			}

			// 下载文件
			fileout = new FileOutputStream(file);
			byte[] buffer = new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}

			is.close();
			fileout.flush();
			fileout.close();

			//以md5对文件进行重命名
			File newfile = new File(filepath + "/" +filename+".jpg");
			if (file.exists()) {
				file.renameTo(newfile);
			}

		} catch (Exception e) {
			//logger.error("下载[" + url + "]出错", e);
		} finally {
			if (is != null) {
				is.close();
			}
			if (fileout != null) {
				fileout.close();
			}
		}

	}
	
}
package com.cas.twitter.crawler.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cas.twitter.crawler.vo.TwitterVO;


@Mapper
public interface TwiMapper {
	
    public void SaveTwi(TwitterVO vo);
    
	public int batchInsertTwitter(List<TwitterVO> list)throws Exception;

}



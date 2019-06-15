package com.cas.twitter.crawler.dao;

import org.apache.ibatis.annotations.Mapper;
import com.cas.twitter.crawler.vo.TwitterVO;

@Mapper
public interface TwiMapper {
	/**
	 * save twitter to db
	 * @param vo
	 */
    public void SaveTwi(TwitterVO vo);
    
}



package com.simba.service.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.simba.common.CustomizedPropertyPlaceholderConfigurer;

/**
 * 微信使用的Redis缓存
 * 
 * @author caozhejun
 *
 */
@Component("wxCache")
public class WxCacheServiceImpl extends RedisByteCacheServiceImpl {

	@PostConstruct
	public void init() {
		setRedisHost(CustomizedPropertyPlaceholderConfigurer.getContextProperty("wx.redis.host"));
		setRedisPort(NumberUtils.toInt(CustomizedPropertyPlaceholderConfigurer.getContextProperty("wx.redis.port"), 6379));
		setRedisPassword(CustomizedPropertyPlaceholderConfigurer.getContextProperty("wx.redis.password"));
		super.init();
	}
}

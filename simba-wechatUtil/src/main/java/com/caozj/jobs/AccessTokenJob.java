package com.caozj.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.caozj.util.common.AccessTokenUtil;

/**
 * 定时获取access_token的定时器(每小时执行一次)
 * 
 * @author caozhejun
 *
 */
@Component
public class AccessTokenJob {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Scheduled(fixedRate = 3600000, initialDelay = 5000)
	public void requestAccessToken() {
		accessTokenUtil.requestAccessToken();
	}

}

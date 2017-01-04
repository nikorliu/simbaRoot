package com.caozj.util.common;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.caozj.model.wx.AccessToken;
import com.caozj.service.CacheService;

/**
 * 用来处理access_token的工具类
 * 
 * @author caozhejun
 *
 */
@Component
public class AccessTokenUtil {

	private static final Log logger = LogFactory.getLog(AccessTokenUtil.class);

	@Value("${appID}")
	private String appID;

	@Value("${appsecret}")
	private String appsecret;

	@Value("${access.token.storage}")
	private String storage;

	@Resource
	private CacheService wxCache;

	@Autowired
	private GetUtil getUtil;

	private String token;

	/**
	 * 请求access_token
	 */
	public void requestAccessToken() {
		String url = WxConstantData.accessTokenUrl + "&appid=" + appID + "&secret=" + appsecret;
		AccessToken accessToken = getUtil.get(url, "请求access_token", AccessToken.class);
		if (StringUtils.isNotEmpty(accessToken.getAccess_token())) {
			logger.info("从微信服务器获取access_token:" + accessToken.getAccess_token() + ",过期时间:" + accessToken.getExpires_in());
			saveAccessToken(accessToken.getAccess_token());
		}
	}

	/**
	 * 保存access_token
	 * 
	 * @param accessToken
	 */
	private void saveAccessToken(String accessToken) {
		if ("local".equals(storage)) {
			token = accessToken;
		} else {
			wxCache.set("wx_access_token", accessToken, 3600);
		}
	}

	/**
	 * 获取access_token
	 * 
	 * @return
	 */
	public String getAccessToken() {
		String result = null;
		if ("local".equals(storage)) {
			result = token;
		} else {
			result = wxCache.get("wx_access_token").toString();
		}
		if (StringUtils.isEmpty(result)) {
			this.requestAccessToken();
			return this.getAccessToken();
		}
		return result;
	}

}

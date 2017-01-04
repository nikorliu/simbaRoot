package com.caozj.util.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caozj.model.wx.autoreply.AutoReplyResult;
import com.caozj.util.common.AccessTokenUtil;
import com.caozj.util.common.GetUtil;
import com.caozj.util.common.WxConstantData;

/**
 * 获取公众号的自动回复规则
 * 
 * @author caozhejun
 *
 */
@Component
public class AutoReplyWxUtil {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private GetUtil getUtil;

	/**
	 * 获取公众号的自动回复规则
	 * 
	 * @return
	 */
	public AutoReplyResult get() {
		String url = WxConstantData.autoReplyUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		return getUtil.get(url, "获取公众号的自动回复规则", AutoReplyResult.class);
	}
}

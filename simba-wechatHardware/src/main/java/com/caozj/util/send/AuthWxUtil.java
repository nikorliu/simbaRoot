package com.caozj.util.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wxHardware.send.AuthDevice;
import com.caozj.model.wxHardware.send.AuthResult;
import com.caozj.util.common.AccessTokenUtil;
import com.caozj.util.common.PostJsonUtil;
import com.caozj.util.common.WxHardwareConstantData;

/**
 * 授权工具类
 * 
 * @author caozhejun
 *
 */
@Component
public class AuthWxUtil {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private PostJsonUtil postJsonUtil;

	/**
	 * 设备授权
	 * 
	 * @param device
	 * @return
	 */
	public AuthResult send(AuthDevice device) {
		String json = FastJsonUtil.toJson(device);
		String url = WxHardwareConstantData.authUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		return postJsonUtil.postJson(url, json, "设备授权", AuthResult.class);
	}
}

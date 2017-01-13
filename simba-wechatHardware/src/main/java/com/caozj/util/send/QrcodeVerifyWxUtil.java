package com.caozj.util.send;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wxHardware.send.QrcodeVerifyResult;
import com.caozj.util.common.AccessTokenUtil;
import com.caozj.util.common.PostJsonUtil;
import com.caozj.util.common.WxHardwareConstantData;

/**
 * 验证二维码
 * 
 * @author caozhejun
 *
 */
@Component
public class QrcodeVerifyWxUtil {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private PostJsonUtil postJsonUtil;

	/**
	 * 验证二维码
	 * 
	 * @param ticket
	 * @return
	 */
	public QrcodeVerifyResult verify(String ticket) {
		Map<String, String> params = new HashMap<>();
		params.put("ticket", ticket);
		String json = FastJsonUtil.toJson(params);
		String url = WxHardwareConstantData.qrcodeVerifyUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		return postJsonUtil.postJson(url, json, "验证二维码", QrcodeVerifyResult.class);
	}
}

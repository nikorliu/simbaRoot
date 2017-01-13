package com.caozj.util.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wxHardware.send.DeviceMsg;
import com.caozj.model.wxHardware.send.DeviceMsgResult;
import com.caozj.util.common.AccessTokenUtil;
import com.caozj.util.common.Base64Util;
import com.caozj.util.common.PostJsonUtil;
import com.caozj.util.common.WxHardwareConstantData;

/**
 * 主动发送消息给设备工具类
 * 
 * @author caozhejun
 *
 */
@Component
public class DeviceMsgWxUtil {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private PostJsonUtil postJsonUtil;

	/**
	 * 发送消息给设备
	 * 
	 * @param msg
	 */
	public void send(DeviceMsg msg) {
		msg.setContent(Base64Util.encode(msg.getContent()));
		String json = FastJsonUtil.toJson(msg);
		String url = WxHardwareConstantData.sendMessageToDeviceUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		postJsonUtil.postJson(url, json, "发送消息给设备", DeviceMsgResult.class);
	}
}

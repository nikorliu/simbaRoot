package com.caozj.util.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wx.ErrMsg;
import com.caozj.model.wx.device.DeviceStatus;
import com.caozj.util.common.AccessTokenUtil;
import com.caozj.util.common.PostJsonUtil;
import com.caozj.util.common.WxConstantData;

/**
 * 第三方主动发送设备状态消息给微信终端
 * 
 * @author caozhejun
 *
 */
@Component
public class DeviceStatusWxUtil {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private PostJsonUtil postJsonUtil;

	/**
	 * 发送设备状态
	 * 
	 * @param status
	 */
	public void send(DeviceStatus status) {
		String url = WxConstantData.deviceStatusUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		String json = FastJsonUtil.toJson(status);
		postJsonUtil.postJson(url, json, "发送设备状态", ErrMsg.class);
	}

}

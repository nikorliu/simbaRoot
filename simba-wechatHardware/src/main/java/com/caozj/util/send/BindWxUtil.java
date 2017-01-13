package com.caozj.util.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wxHardware.send.Bind;
import com.caozj.model.wxHardware.send.BindForce;
import com.caozj.model.wxHardware.send.BindResult;
import com.caozj.util.common.AccessTokenUtil;
import com.caozj.util.common.PostJsonUtil;
import com.caozj.util.common.WxHardwareConstantData;

/**
 * 绑定/解绑工具类
 * 
 * @author caozhejun
 *
 */
@Component
public class BindWxUtil {

	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private PostJsonUtil postJsonUtil;

	/**
	 * 绑定成功通知
	 * 
	 * @param bind
	 * @return
	 */
	public BindResult bind(Bind bind) {
		String json = FastJsonUtil.toJson(bind);
		String url = WxHardwareConstantData.bindUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		return postJsonUtil.postJson(url, json, "绑定成功通知", BindResult.class);
	}

	/**
	 * 解绑成功通知
	 * 
	 * @param bind
	 * @return
	 */
	public BindResult unbind(Bind bind) {
		String json = FastJsonUtil.toJson(bind);
		String url = WxHardwareConstantData.unbindUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		return postJsonUtil.postJson(url, json, "解绑成功通知", BindResult.class);
	}

	/**
	 * 强制绑定用户和设备
	 * 
	 * @param bind
	 * @return
	 */
	public BindResult bindForce(BindForce bind) {
		String json = FastJsonUtil.toJson(bind);
		String url = WxHardwareConstantData.bindForceUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		return postJsonUtil.postJson(url, json, "强制绑定用户和设备", BindResult.class);
	}

	/**
	 * 强制解绑用户和设备
	 * 
	 * @param bind
	 * @return
	 */
	public BindResult unbindForce(BindForce bind) {
		String json = FastJsonUtil.toJson(bind);
		String url = WxHardwareConstantData.unbindForceUrl + "?access_token=" + accessTokenUtil.getAccessToken();
		return postJsonUtil.postJson(url, json, "强制解绑用户和设备", BindResult.class);
	}
}

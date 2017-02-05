package com.caozj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wxHardware.send.BindForce;
import com.caozj.model.wxHardware.send.Device;
import com.caozj.model.wxHardware.send.DeviceInfo;
import com.caozj.model.wxHardware.send.GetDeviceQrcodeResult;
import com.caozj.model.wxHardware.send.StatusResult;
import com.caozj.util.send.BindWxUtil;
import com.caozj.util.send.NewAuthWxUtil;
import com.caozj.util.send.StatusWxUtil;

/**
 * 用来提供给第三方平台的操作Controller
 * 
 * @author caozhejun
 *
 */
@Controller
@RequestMapping("/operHardware")
public class OperHardwareController {

	@Autowired
	private BindWxUtil bindWxUtil;

	@Autowired
	private StatusWxUtil statusWxUtil;

	@Autowired
	private NewAuthWxUtil newAuthWxUtil;

	/**
	 * 解除设备绑定
	 * 
	 * @param wxUserDeviceId
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String unbindDevice(String openid, String deviceId, ModelMap model) {
		BindForce unbind = new BindForce();
		unbind.setOpenid(openid);
		unbind.setDevice_id(deviceId);
		bindWxUtil.unbindForce(unbind);
		model.put("message", FastJsonUtil.toJson(new JsonResult()));
		return "message";
	}

	/**
	 * 查询设备状态
	 * 
	 * @param deviceId
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String searchDeviceStatus(String deviceId, ModelMap model) {
		StatusResult result = statusWxUtil.search(deviceId);
		model.put("message", FastJsonUtil.toJson(new JsonResult(result.getStatus(), result.getStatus_info(), 200)));
		return "message";
	}

	/**
	 * 生成二维码和对应的设备id
	 * 
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String genQrcode(String productId, ModelMap model) {
		GetDeviceQrcodeResult result = newAuthWxUtil.getDeviceQrcode(productId);
		model.put("message", FastJsonUtil.toJson(new JsonResult(result)));
		return "message";
	}

	/**
	 * 利用deviceid更新设备属性
	 * 
	 * @param device
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String updateDevicePros(Device device, ModelMap model) {
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDevice_num("1");
		deviceInfo.setOp_type("1");
		List<Device> device_list = new ArrayList<Device>(1);
		device_list.add(device);
		deviceInfo.setDevice_list(device_list);
		newAuthWxUtil.updateDeviceInfo(deviceInfo);
		model.put("message", FastJsonUtil.toJson(new JsonResult()));
		return "message";
	}

}

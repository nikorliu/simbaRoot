package com.caozj.model.wxHardware.send;

import com.caozj.model.wx.ErrMsg;

public class AuthRes extends ErrMsg {

	private DeviceBaseInfo base_info;

	public DeviceBaseInfo getBase_info() {
		return base_info;
	}

	public void setBase_info(DeviceBaseInfo base_info) {
		this.base_info = base_info;
	}

}

package com.caozj.model.wxHardware.send;

import com.caozj.model.wx.ErrMsg;

public class BindResult extends ErrMsg {

	private ErrMsg base_resp;

	public ErrMsg getBase_resp() {
		return base_resp;
	}

	public void setBase_resp(ErrMsg base_resp) {
		this.base_resp = base_resp;
	}

}

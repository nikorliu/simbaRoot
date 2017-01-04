package com.caozj.model.wx.template;

import com.caozj.model.wx.ErrMsg;

public class TemplateSendResult extends ErrMsg {

	private long msgid;

	public long getMsgid() {
		return msgid;
	}

	public void setMsgid(long msgid) {
		this.msgid = msgid;
	}

}

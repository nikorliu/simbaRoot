package com.caozj.model.wx.group.openid;

import com.caozj.model.wx.msg.Text;

public class GroupText {

	private ToUser touser;;

	private Text text;

	private String msgtype = "text";

	public ToUser getTouser() {
		return touser;
	}

	public void setTouser(ToUser touser) {
		this.touser = touser;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}

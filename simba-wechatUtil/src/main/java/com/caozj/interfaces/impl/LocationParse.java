package com.caozj.interfaces.impl;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.xerces.dom.DeferredElementImpl;

import com.caozj.interfaces.ParseWxXML;
import com.caozj.model.wx.receive.BaseReceiveObject;
import com.caozj.model.wx.receive.msg.LocationMessage;
import com.caozj.util.common.ParseUtil;

public class LocationParse implements ParseWxXML {

	@Override
	public BaseReceiveObject parse(DeferredElementImpl root) throws XPathExpressionException {
		LocationMessage message = new LocationMessage();
		message.setToUserName(ParseUtil.getToUserName(root));
		message.setFromUserName(ParseUtil.getFromUserName(root));
		message.setMsgId(ParseUtil.getMsgId(root));
		message.setCreateTime(NumberUtils.toLong(ParseUtil.getCreateTime(root)));
		message.setMsgType(ParseUtil.getMsgType(root));
		message.setX(NumberUtils.toDouble(ParseUtil.getValue(root, "Location_X")));
		message.setY(NumberUtils.toDouble(ParseUtil.getValue(root, "Location_Y")));
		message.setScale(NumberUtils.toDouble(ParseUtil.getValue(root, "Scale")));
		message.setLabel(ParseUtil.getValue(root, "Label"));
		return message;
	}

}

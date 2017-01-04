package com.caozj.interfaces.impl;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.xerces.dom.DeferredElementImpl;

import com.caozj.interfaces.ParseWxXML;
import com.caozj.model.wx.receive.BaseReceiveObject;
import com.caozj.model.wx.receive.msg.TextMessage;
import com.caozj.util.common.ParseUtil;

public class TextParse implements ParseWxXML {

	@Override
	public BaseReceiveObject parse(DeferredElementImpl root) throws XPathExpressionException {
		TextMessage text = new TextMessage();
		text.setToUserName(ParseUtil.getToUserName(root));
		text.setFromUserName(ParseUtil.getFromUserName(root));
		text.setMsgId(ParseUtil.getMsgId(root));
		text.setCreateTime(NumberUtils.toLong(ParseUtil.getCreateTime(root)));
		text.setMsgType(ParseUtil.getMsgType(root));
		text.setContent(ParseUtil.getValue(root, "Content"));
		return text;
	}

}

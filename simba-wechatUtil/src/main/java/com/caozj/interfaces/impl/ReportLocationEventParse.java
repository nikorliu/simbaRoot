package com.caozj.interfaces.impl;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.xerces.dom.DeferredElementImpl;

import com.caozj.interfaces.ParseWxXML;
import com.caozj.model.wx.receive.BaseReceiveObject;
import com.caozj.model.wx.receive.event.ReportLocationEvent;
import com.caozj.util.common.ParseUtil;

public class ReportLocationEventParse implements ParseWxXML {

	@Override
	public BaseReceiveObject parse(DeferredElementImpl root) throws XPathExpressionException {
		ReportLocationEvent event = new ReportLocationEvent();
		event.setToUserName(ParseUtil.getToUserName(root));
		event.setFromUserName(ParseUtil.getFromUserName(root));
		event.setCreateTime(NumberUtils.toLong(ParseUtil.getCreateTime(root)));
		event.setMsgType(ParseUtil.getMsgType(root));
		event.setEvent(ParseUtil.getValue(root, "Event"));
		event.setEventKey(ParseUtil.getValue(root, "EventKey"));
		event.setLatitude(NumberUtils.toDouble(ParseUtil.getValue(root, "Latitude")));
		event.setLongitude(NumberUtils.toDouble(ParseUtil.getValue(root, "Longitude")));
		event.setPrecision(NumberUtils.toDouble(ParseUtil.getValue(root, "Precision")));
		return event;
	}

}

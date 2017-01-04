package com.caozj.interfaces.impl;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.xerces.dom.DeferredElementImpl;

import com.caozj.interfaces.ParseWxXML;
import com.caozj.model.wx.receive.BaseReceiveObject;
import com.caozj.model.wx.receive.event.TicketEvent;
import com.caozj.util.common.ParseUtil;

public class TicketEventParse implements ParseWxXML {

	@Override
	public BaseReceiveObject parse(DeferredElementImpl root) throws XPathExpressionException {
		TicketEvent event = new TicketEvent();
		event.setToUserName(ParseUtil.getToUserName(root));
		event.setFromUserName(ParseUtil.getFromUserName(root));
		event.setCreateTime(NumberUtils.toLong(ParseUtil.getCreateTime(root)));
		event.setMsgType(ParseUtil.getMsgType(root));
		event.setEvent(ParseUtil.getValue(root, "Event"));
		event.setEventKey(ParseUtil.getValue(root, "EventKey"));
		event.setTicket(ParseUtil.getValue(root, "Ticket"));
		return event;
	}

}

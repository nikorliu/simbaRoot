package com.caozj.interfaces;

import javax.xml.xpath.XPathExpressionException;

import org.apache.xerces.dom.DeferredElementImpl;

import com.caozj.model.wx.receive.BaseReceiveObject;

public interface ParseWxXML {

	BaseReceiveObject parse(DeferredElementImpl root) throws XPathExpressionException;
}

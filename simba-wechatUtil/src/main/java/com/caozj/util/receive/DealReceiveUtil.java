package com.caozj.util.receive;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.caozj.framework.util.common.ReflectUtil;
import com.caozj.framework.util.common.XmlUtil;
import com.caozj.model.wx.receive.conf.ConfType;

/**
 * 处理微信发送的消息的工具类
 * 
 * @author caozhejun
 *
 */
@Component
public class DealReceiveUtil {

	@Value("${wx.deal}")
	private String wxDeal;

	private Map<String, Map<String, ConfType>> typeMap = new HashMap<>();

	@PostConstruct
	private void init() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		InputStream in = DealReceiveUtil.class.getResourceAsStream("/wx.xml");
		Document doc = XmlUtil.parseXMLStream(in);
		Element root = doc.getDocumentElement();
		typeMap.put("event", parse("/wx/events/event", root));
		typeMap.put("msg", parse("/wx/msgs/msg", root));
	}

	private Map<String, ConfType> parse(String xpath, Element root) throws XPathExpressionException {
		Map<String, ConfType> map = new HashMap<>();
		NodeList nodeList = XmlUtil.selectNodes(xpath, root);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element node = (Element) (nodeList.item(i));
			String type = node.getAttribute("type");
			String parser = node.getAttribute("parser");
			String dealMethod = node.getAttribute("dealMethod");
			ConfType f = new ConfType();
			f.setDealMethod(dealMethod);
			f.setParser(parser);
			f.setType(type);
			map.put(type, f);
		}
		return map;
	}

	/**
	 * 处理微信发送的消息
	 * 
	 * @param xml
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@Async
	public void deal(String xml) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		Document doc = XmlUtil.parseXMLContent(xml);
		Element root = doc.getDocumentElement();
		Element msgType = (Element) XmlUtil.selectSingleNode("/xml/MsgType", root);
		String type = msgType.getTextContent();
		ConfType conf = null;
		if (type.equalsIgnoreCase("event")) {
			Element event = (Element) XmlUtil.selectSingleNode("/xml/Event", root);
			String key = event.getTextContent();
			conf = typeMap.get("event").get(key);
		} else {
			conf = typeMap.get("msg").get(type);
		}
		if (conf == null) {
			throw new RuntimeException("配置文件wx.xml中找不到可以处理的配置:" + xml);
		}
		String parser = conf.getParser();
		String dealMethod = conf.getDealMethod();
		Object object = ReflectUtil.invokeObjectMethod(parser, "parse", root);
		ReflectUtil.invokeObjectMethod(wxDeal, dealMethod, object);
	}
}

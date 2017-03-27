package com.simba.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simba.framework.util.http.HttpClientUtil;

/**
 * 微信管理菜单Controller
 * 
 * @author caozhejun
 *
 */
@Controller
@RequestMapping("/wxMenu")
public class WxMenuController {

	@Value("${wx.domain}")
	private String wxDomain;

	/**
	 * 进入微信菜单管理界面
	 * 
	 * @return
	 */
	@RequestMapping
	public String view() {
		return "wx/menu";
	}

	/**
	 * 查询微信自定义菜单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String search(ModelMap model) {
		String url = wxDomain + "/oper/search.do?json";
		model.put("message", HttpClientUtil.get(url));
		return "message";
	}

	/**
	 * 创建微信自定义菜单
	 * 
	 * @param menuInfo
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String create(String menuInfo, ModelMap model) {
		String url = wxDomain + "/oper/create.do?json";
		Map<String, String> params = new HashMap<>();
		params.put("menuInfo", menuInfo);
		String result = HttpClientUtil.post(url, params);
		model.put("message", result);
		return "message";
	}

}

package com.caozj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.json.FastJsonUtil;
import com.caozj.model.wx.menu.Menu;
import com.caozj.model.wx.menu.MenuSearchResult;
import com.caozj.util.send.MenuWxUtil;

/**
 * 用来提供给第三方平台的操作Controller
 * 
 * @author caozhejun
 *
 */
@Controller
@RequestMapping("/oper")
public class OperController {

	@Autowired
	private MenuWxUtil menuWxUtil;

	/**
	 * 查询菜单
	 * 
	 * @param model
	 */
	@RequestMapping
	public String search(ModelMap model) {
		MenuSearchResult result = menuWxUtil.search();
		model.put("message", FastJsonUtil.toJson(new JsonResult(result)));
		return "message";
	}

	/**
	 * 创建菜单
	 * 
	 * @param menuInfo
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String create(String menuInfo, ModelMap model) {
		Menu menu = FastJsonUtil.toObject(menuInfo, Menu.class);
		menuWxUtil.create(menu);
		model.put("message", FastJsonUtil.toJson(new JsonResult()));
		return "message";
	}
}

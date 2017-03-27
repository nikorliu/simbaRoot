package com.simba.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simba.activiti.util.ActivitiObjectUtil;
import com.simba.activiti.vo.ActivityVo;
import com.simba.framework.model.easyui.PageGrid;
import com.simba.framework.model.json.JsonResult;
import com.simba.framework.util.json.JsonUtil;
import com.simba.permission.model.User;
import com.simba.permission.service.UserService;

/**
 * 流程工具Controller
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processUtil")
public class ProcessUtilController {

	@Autowired
	private HistoryService historyService;

	@Autowired
	private UserService userService;

	/**
	 * 获取当前用户对象
	 * 
	 * @param sessAccount
	 * @param sessName
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCurrentUser.do")
	public String getCurrentUser(String sessAccount, String sessName, ModelMap model) {
		User user = new User();
		user.setAccount(sessAccount);
		user.setName(sessName);
		model.put("message", new JsonResult(user).toJson());
		return "message";
	}

	/**
	 * 获取流程的所有活动记录
	 * 
	 * @param processInstanceId
	 *            流程实例Id
	 * @param model
	 * @return
	 */
	@RequestMapping("/getHistoryActivity.do")
	public String getHistoryActivity(String processInstanceId, ModelMap model) {
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
		int total = list.size();
		List<ActivityVo> voList = new ArrayList<>(total);
		list.forEach((activity) -> {
			ActivityVo vo = ActivitiObjectUtil.buildActivityVo(activity);
			vo.setAssignee(userService.getDesc(vo.getAssignee()));
			voList.add(vo);
		});
		String message = JsonUtil.toJson(new PageGrid(total, voList));
		model.put("message", message);
		return "message";
	}

}

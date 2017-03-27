package com.simba.activiti.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simba.activiti.form.TaskSearchForm;
import com.simba.activiti.util.ActivitiObjectUtil;
import com.simba.activiti.vo.TaskVo;
import com.simba.form.EasyUIPageForm;
import com.simba.framework.model.easyui.PageGrid;
import com.simba.framework.util.json.JsonUtil;
import com.simba.model.constant.ConstantData;
import com.simba.permission.service.UserService;

/**
 * 已办任务
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processDone")
public class ProcessDoneController {

	@Autowired
	private HistoryService historyService;

	@Autowired
	private UserService userService;

	@RequestMapping("/list.do")
	public String list() {
		return "activiti/processDoneList";
	}

	@RequestMapping("/listDataOfEasyUI.do")
	public String listDataOfEasyUI(ModelMap model, EasyUIPageForm form, String sessAccount, TaskSearchForm searchForm) {
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery().taskAssignee(sessAccount);
		if (StringUtils.isNotEmpty(searchForm.getProcessName())) {
			query.processDefinitionNameLike("%" + searchForm.getProcessName() + "%");
		}
		if (StringUtils.isNotEmpty(searchForm.getTaskName())) {
			query.taskNameLike("%" + searchForm.getTaskName() + "%");
		}
		query = query.taskCompletedBefore(new Date());
		query = query.orderByHistoricTaskInstanceEndTime().desc();
		List<HistoricTaskInstance> list = query.listPage((form.getPage() - 1) * form.getRows(), form.getRows());
		int total = NumberUtils.toInt(query.count() + "");
		List<TaskVo> voList = new ArrayList<>(list.size());
		list.forEach((task) -> {
			TaskVo vo = ActivitiObjectUtil.buildTaskVo(task);
			vo.setAssigneeName(userService.getDesc(vo.getAssignee()));
			HistoricVariableInstance variableInstance = historyService.createHistoricVariableInstanceQuery().processInstanceId(vo.getProcessInstanceId()).variableName(ConstantData.TITLE)
					.singleResult();
			if (variableInstance != null) {
				vo.setTitle(variableInstance.getValue().toString());
			}
			voList.add(vo);
		});
		String message = JsonUtil.toJson(new PageGrid(total, voList));
		model.put("message", message);
		return "message";
	}
}

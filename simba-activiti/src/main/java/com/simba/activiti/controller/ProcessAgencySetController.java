package com.simba.activiti.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simba.activiti.model.ProcessAgencySet;
import com.simba.activiti.service.ProcessAgencySetService;
import com.simba.form.EasyUIPageForm;
import com.simba.framework.model.easyui.PageGrid;
import com.simba.framework.model.json.JsonResult;
import com.simba.framework.util.jdbc.Pager;
import com.simba.framework.util.json.JsonUtil;

/**
 * Controller
 * 
 * @author caozj
 * 
 */
@Controller
@RequestMapping("/processAgencySet")
public class ProcessAgencySetController {

  @Autowired
  private ProcessAgencySetService processAgencySetService;

  @RequestMapping("/list.do")
  public String list(ModelMap model) {
    return "activiti/listProcessAgencySet";
  }

  @RequestMapping("/listDataOfEasyUI.do")
	public String listDataOfEasyUI(ModelMap model, EasyUIPageForm form) {
    Pager page = new Pager((form.getPage() - 1) * form.getRows(), form.getRows());
    List<ProcessAgencySet> list = processAgencySetService.page(page);
    String message = JsonUtil.toJson(new PageGrid(page.getTotalCount(), list));
    model.put("message", message);
    return "message";
  }

  @RequestMapping("/toAdd.do")
  public String toAdd() {
    return "activiti/addProcessAgencySet";
  }

  @RequestMapping("/toUpdate.do")
  public String toUpdate(int id, ModelMap model) {
    ProcessAgencySet processAgencySet = processAgencySetService.get(id);
    model.put("processAgencySet", processAgencySet);
    return "activiti/updateProcessAgencySet";
  }

  @RequestMapping("/add.do")
  public String add(ProcessAgencySet processAgencySet, ModelMap model, String sessAccount) {
    processAgencySet.setAccount(sessAccount);
    processAgencySetService.add(processAgencySet);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  @RequestMapping("/update.do")
  public String update(ProcessAgencySet processAgencySet, ModelMap model, String sessAccount) {
    processAgencySet.setAccount(sessAccount);
    processAgencySetService.update(processAgencySet);
    model.put("message", JsonUtil.successJson());
    return "message";
  }

  @RequestMapping("/batchDelete.do")
  public String batchDelete(Integer[] ids, ModelMap model) {
    List<Integer> idList = Arrays.asList(ids);
    processAgencySetService.batchDelete(idList);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  @RequestMapping("/delete.do")
  public String delete(int id, ModelMap model) {
    processAgencySetService.delete(id);
    model.put("message", JsonUtil.successJson());
    return "message";
  }

  @RequestMapping("/show.do")
  public String show(int id, ModelMap model) {
    ProcessAgencySet processAgencySet = processAgencySetService.get(id);
    model.put("processAgencySet", processAgencySet);
    return "processAgencySet/show";
  }

  @RequestMapping("/get.do")
  public String get(int id, ModelMap model) {
    ProcessAgencySet processAgencySet = processAgencySetService.get(id);
    model.put("message", new JsonResult(processAgencySet).toJson());
    return "message";
  }

}

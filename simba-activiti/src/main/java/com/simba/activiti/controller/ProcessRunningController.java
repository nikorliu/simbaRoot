package com.simba.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 运行中流程
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processRunning")
public class ProcessRunningController {

	@RequestMapping("/list.do")
	public String list() {
		return "activiti/processRunningList";
	}

}

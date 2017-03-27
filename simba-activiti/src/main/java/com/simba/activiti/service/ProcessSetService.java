package com.simba.activiti.service;

import com.simba.activiti.model.ProcessSet;

/**
 * 流程设置Service
 * 
 * @author caozj
 * 
 */
public interface ProcessSetService {

  void add(ProcessSet processSet);

  void update(ProcessSet processSet);

  void delete(String id);

  ProcessSet get(String id);

}

package com.simba.activiti.dao;

import com.simba.activiti.model.ProcessSet;

/**
 * 流程设置Dao
 * 
 * @author caozj
 * 
 */
public interface ProcessSetDao {

  void add(ProcessSet processSet);

  void update(ProcessSet processSet);

  void delete(String id);

  ProcessSet get(String id);

}

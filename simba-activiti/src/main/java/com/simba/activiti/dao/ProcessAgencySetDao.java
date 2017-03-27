package com.simba.activiti.dao;

import java.util.List;

import com.simba.activiti.model.ProcessAgencySet;
import com.simba.framework.util.jdbc.Pager;

/**
 *  Dao
 * 
 * @author caozj
 * 
 */
public interface ProcessAgencySetDao {

	void add(ProcessAgencySet processAgencySet);

	void update(ProcessAgencySet processAgencySet);

	void delete(int id);

	List<ProcessAgencySet> listAll();

	int count();
	
	int countBy(String field, Object value);
	
	List<ProcessAgencySet> page(Pager page);

	ProcessAgencySet get(int id);
	
	ProcessAgencySet getBy(String field, Object value);

	ProcessAgencySet getByAnd(String field1, Object value1, String field2, Object value2);

	ProcessAgencySet getByOr(String field1, Object value1, String field2, Object value2);

	List<ProcessAgencySet> listBy(String field, Object value);

	List<ProcessAgencySet> listByAnd(String field1, Object value1, String field2, Object value2);

	List<ProcessAgencySet> listByOr(String field1, Object value1, String field2, Object value2);

	List<ProcessAgencySet> pageBy(String field, Object value, Pager page);

	List<ProcessAgencySet> pageByAnd(String field1, Object value1, String field2, Object value2, Pager page);

	List<ProcessAgencySet> pageByOr(String field1, Object value1, String field2, Object value2, Pager page);

}

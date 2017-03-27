package com.simba.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.simba.service.CacheService;

/**
 * 对象池
 * 
 * @author caozhejun
 *
 */
@Component
public class ObjectPool {

	@Value("${object.storage}")
	private String storage;

	@Resource
	private CacheService redisByteService;

	private static final Map<String, Object> data = new HashMap<>();

	/**
	 * 保存对象
	 * 
	 * @param key
	 * @param value
	 */
	public void save(String key, String value) {
		if ("local".equals(storage)) {
			data.put(key, value);
		} else {
			redisByteService.set(key, value);
		}
	}

	/**
	 * 获取对象
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		Object result = null;
		if ("local".equals(storage)) {
			result = data.get(key);
		} else {
			result = redisByteService.get(key);
		}
		return result;
	}

	/**
	 * 删除对象
	 * 
	 * @param key
	 */
	public void remove(String key) {
		if ("local".equals(storage)) {
			data.remove(key);
		} else {
			redisByteService.remove(key);
		}
	}
}

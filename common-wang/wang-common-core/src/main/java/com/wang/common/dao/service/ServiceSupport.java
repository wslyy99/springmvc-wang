package com.wang.common.dao.service;

import java.util.Map;

import com.wang.common.entity.QueryResult;


public interface ServiceSupport<T> {

	void insert(T clazz);

	void insertSelective(T clazz);

	void deleteByPrimaryKey(Integer id);

	void updateByPrimaryKey(T clazz);

	void updateByPrimaryKeySelective(T clazz);
	
	void saveOrUpdate(T clazz);

	T selectByPrimaryKey(Integer id);
	
	QueryResult<T> list();

	QueryResult<T> list(Map<String, Object> params);
	
	QueryResult<T> list(T clazz);
	
	


}
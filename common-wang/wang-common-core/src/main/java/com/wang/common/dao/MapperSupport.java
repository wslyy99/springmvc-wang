package com.wang.common.dao;

import java.util.List;
import java.util.Map;

public abstract interface MapperSupport<T>
{

	int deleteByPrimaryKey(Integer id);
	
	int deleteByParams(Map<String, Object> params);

    int insert(T entity);

    int insertSelective(T entity);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);

	List<T> selectByMapParams(Map<String, Object> params);

	Integer countByMapParams(Map<String, Object> params);
	
	List<T> selectByEntityParams(T clazz);

	Integer countByEntityParams(T clazz);
	
}
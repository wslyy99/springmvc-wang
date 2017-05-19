package com.wang.common.dao.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wang.common.dao.MapperSupport;
import com.wang.common.dao.service.ServiceSupport;
import com.wang.common.entity.QueryResult;


public abstract   class ServiceSupportImpl<T> implements ServiceSupport<T>
{
	
    public abstract  MapperSupport<T> getMapperSupport();
    
	@Override
	public void insert(T clazz) {
		this.getMapperSupport().insert(clazz);
	}
	
	@Override
	public void insertSelective(T clazz) {
		this.getMapperSupport().insertSelective(clazz);
	}

	@Override
	public void deleteByPrimaryKey(Integer id) {
		this.getMapperSupport().deleteByPrimaryKey(id);
	}
	
	@Override
	public void updateByPrimaryKey(T clazz) {
		this.getMapperSupport().updateByPrimaryKey(clazz);
	}
	
	@Override
	public void updateByPrimaryKeySelective(T clazz) {
		this.getMapperSupport().updateByPrimaryKeySelective(clazz);
	}
	
	@Override
	public void saveOrUpdate(T clazz) {
		try {
			Method method=clazz.getClass().getMethod("getId");
			Object object=method.invoke(clazz);
			if(object!=null){
				this.updateByPrimaryKeySelective(clazz);
			}else{
				this.insertSelective(clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public T selectByPrimaryKey(Integer id) {
		return this.getMapperSupport().selectByPrimaryKey(id);
	}
	
	
	@Override
	public QueryResult<T> list(Map<String, Object> params) {
		QueryResult<T> queryResult=new QueryResult<>();
		List<T> list=this.getMapperSupport().selectByMapParams(params);
		Integer count=this.getMapperSupport().countByMapParams(params);
		queryResult.setQueryResult(list);
		queryResult.setCount(count);
		return queryResult;
	}
	
	@Override
	public QueryResult<T> list() {
		Map<String, Object> params=new HashMap<String, Object>();
		return this.list(params);
	}
	
	@Override
	public QueryResult<T> list(T clazz) {
		QueryResult<T> queryResult=new QueryResult<>();
		List<T> list=this.getMapperSupport().selectByEntityParams(clazz);
		Integer count=this.getMapperSupport().countByEntityParams(clazz);
		queryResult.setQueryResult(list);
		queryResult.setCount(count);
		return queryResult;
	}

}
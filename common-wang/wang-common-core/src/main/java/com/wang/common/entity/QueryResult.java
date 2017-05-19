package com.wang.common.entity;

import java.util.List;
import java.util.Map;

public class QueryResult<T>{
	
	private List<T> queryResult;
	private List<Map<String, Object>> queryResultMap;
	private long count;

	public List<T> getQueryResult() {
		return this.queryResult;
	}

	public void setQueryResult(List<T> queryResult) {
		this.queryResult = queryResult;
	}

	public long getCount() {
		return this.count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<Map<String, Object>> getQueryResultMap() {
		return queryResultMap;
	}

	public void setQueryResultMap(List<Map<String, Object>> queryResultMap) {
		this.queryResultMap = queryResultMap;
	}
}

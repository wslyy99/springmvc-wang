package com.wang.dao.demo;

import java.util.List;

import com.wang.entity.demo.Demo;

public interface DemoMapper {
    
	int insert(Demo record);

    int insertSelective(Demo record);

    int updateByPrimaryKeySelective(Demo record);

    int updateByPrimaryKey(Demo record);
    
    int deleteByPrimaryKey(Long id);
    
    /**
     * 主键查询
     * 
     * @param id
     * @return
     */
    Demo selectByPrimaryKey(Integer id);
	
    /**
     * 插入记录
     * 
     * @param demo
     * @return
     */
   	int insertEntity(Demo demo);
   	
    /**
     * 分页条件查询
     * 
     * @param page
     * @param example
     * @return
     */
    List<Demo> findDemolistPageByParam(Demo demo);
}
package com.wang.facade.demo;

import java.util.Map;
import com.wang.entity.demo.Demo;




public interface IDemoService {

	/**
	 * 根据主键id查询出来对应的name字段
	 * @param id
	 * @return
	 */
	public String selectNameByPrimaryKey(Integer id);
	
	/**
	 * 插入操作，插入实体数据
	 * @param demo
	 * @return
	 */
	public int insertEntity(Demo demo);
	
	/**
	 * 插入操作，插入两个实体类信息，用来测试事务操作相关
	 * @param demo1
	 * @param demo2
	 * @return
	 */
	public int insert2Entity(Demo demo1, Demo demo2);
	
	/**
     * 根据名称查询实体
     * 
     * @param username
     * @return
     */
	public Demo selectByName(String name);
    
    /**
     * 分页条件查询
     * 
     * @param page
     * @param example
     * @return
     */
	public  Map<String,Object> findDemolistPageByParam(Demo demo);
}
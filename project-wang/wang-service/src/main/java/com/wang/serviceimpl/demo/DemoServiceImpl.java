package com.wang.serviceimpl.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wang.common.page.Page;
import com.wang.dao.demo.DemoMapper;
import com.wang.entity.demo.Demo;
import com.wang.facade.demo.IDemoService;

@Service(value = "demoService")
public class DemoServiceImpl implements IDemoService
{
	private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	
    @Resource(name = "demoMapper")
    private DemoMapper demoMapper;

    @Override
    public String selectNameByPrimaryKey(Integer id)
    {
        logger.info("test11111111111111111你好 ");
        Demo demo = demoMapper.selectByPrimaryKey(id);
        if (demo == null)
        {
            return null;
        }
        else
        {
            return demo.getName();
        }
    }

    /**
     * 此方法返回主键id 如果insert操作更新的行数 >0 的话，返回主键id; 如果更新的行数不大于0的话，返回0；
     */
    @Override
    public int insertEntity(Demo demo)
    {
        int insertEntityReturnId = demoMapper.insertEntity(demo);
        if (insertEntityReturnId > 0)
        {
            return demo.getId();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int insert2Entity(Demo demo1, Demo demo2)
    {
        int insertEntity1ReturnId = demoMapper.insertEntity(demo1);
        int insertEntity2ReturnId = demoMapper.insertEntity(demo2);
        if (insertEntity1ReturnId > 0 && insertEntity2ReturnId > 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public Demo selectByName(String name)
    {
        return null;
    }

    @Override
    public  Map<String,Object> findDemolistPageByParam(Demo demo)
    {
        // TODO Auto-generated method stub
        List<Demo> list = demoMapper.findDemolistPageByParam(demo);
        Map<String,Object> map = new HashMap<String,Object>();
        Page page=demo.getPage();
        map.put("page",page);
		map.put("list", list);
        logger.info("service 总记录数:"+demo.getPage().getTotalResult());
        return map;
    }
}
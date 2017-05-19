package com.wang.controller.demo;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wang.base.BaseController;
import com.wang.common.entity.OutResultDto;
import com.wang.common.page.Page;
import com.wang.entity.demo.Demo;
import com.wang.facade.demo.IDemoService;

@Api(value="测试Demo-api")
@Controller
@RequestMapping(value="${appPath}/demo")
public class DemoController extends BaseController {
	
	@Autowired
	private IDemoService demoService;


	@ApiOperation(value = "测试主页", httpMethod = "GET", notes = "测试信息以json格式传递", response = OutResultDto.class)
	@ApiResponse(code = 200, message = "success", response = OutResultDto.class)
	@ApiImplicitParam(name = "path", value = "url上的数据", required = true, paramType = "path", dataType = "Long")
	@RequestMapping(value = "/index/{path}", method = RequestMethod.GET)
	//@ApiParam(required = true, name = "postData", value = "传入参数描述")
	public ModelAndView home(ModelAndView model, HttpServletRequest request,@PathVariable Long path) {
		logger.info("你好Index Controller");
		/*if("a".equals("a"))
		    throw new UserBizException(UserBizException.USER_IS_NULL,"支付产品已关联用户，无法删除！");*/
		
		String dubboTestString = demoService.selectNameByPrimaryKey(54);
		model.addObject("dubboTestString", dubboTestString);
		model.setViewName("demo/index");
		return model;
	}
	
	@ApiOperation(value = "测试分页", httpMethod = "GET",produces="application/json")
	@ApiImplicitParam(name = "page", value = "文档对象", required = true, paramType = "body", dataType = "Page")
	@RequestMapping(value = "/page")
	@ResponseBody
	public List<Demo> page(Page page) {
		logger.info( "分页测试");
		Page pagebean=new Page();
		pagebean.setCurrentPage(2);
		pagebean.setShowCount(5);
		Demo demo=new Demo();
	    demo.setPage(pagebean);
		demo.setDeleteflag("0");
		
		Map<String,Object> map = demoService.findDemolistPageByParam(demo);
		List<Demo> listdemo = (List<Demo>) map.get("list");
		pagebean = (Page) map.get("page");
		System.out.println("service 总记录数:"+pagebean.getTotalResult());
		logger.info(listdemo.size()+"");
		return listdemo;
	}
	

	@ApiOperation(value = "test", httpMethod = "GET",produces="application/json")
	@ResponseBody
	@RequestMapping(value = "/test")
	public Demo test(ModelAndView model, HttpServletRequest request) {
		//String dubboTestString = demoService.selectNameByPrimaryKey(54);
		Demo demo=new Demo();
		demo.setName("王帅");
		demo.setId(1);
		demo.setDeleteflag("2");;
		demo.setCreatetime(new Date());
		//String jsonString = JSON.toJSONString(demo);
        //System.out.println(jsonString);

		return demo;
	}
	
	@ApiOperation(value = "save", httpMethod = "GET")
	@RequestMapping(value = "/save")
	public ModelAndView save(ModelAndView model) {
		Demo demo = new Demo();
		demo.setCreatetime(new Date());
		demo.setDeleteflag("0");
		demo.setName(new Date().toString());
		
		int demoId = demoService.insertEntity(demo);
		
		model.addObject("demo", demo);
		model.addObject("demoId", demoId);
		
		model.setViewName("jsp/demo/save");
		return model;
	}
}

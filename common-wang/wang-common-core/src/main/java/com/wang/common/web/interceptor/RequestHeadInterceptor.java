package com.wang.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Strings;
import com.wang.common.log.LogControllerAspect;


public class RequestHeadInterceptor extends HandlerInterceptorAdapter {
    
	private  org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	
	@Override  
	public boolean preHandle(HttpServletRequest request,  
	     HttpServletResponse response, Object handler) throws Exception {
		   String version = request.getHeader("version");
		   String apptype=request.getHeader("apptype");
		   String apppin=request.getHeader("apppin");
		   if(!Strings.isNullOrEmpty(version) && !Strings.isNullOrEmpty(apptype) && !Strings.isNullOrEmpty(apppin))
		   {  
			   logger.info("version:"+version);
		       logger.info("apptype:"+apptype);
			   logger.info("apppin:"+apppin);
		   }
		   else
		   {
			   logger.info("=====================请求头数据为空或数据不完整=====================");
		   }
	       return true;
	}
	
	 public void postHandle(    
	            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)    
	            throws Exception {
		 
	 }    
	 
	 /**根据ex是否为null判断是否发生了异常，进行日志记录。 **/
	 public void afterCompletion(    
	            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)    
	            throws Exception {
		 
	}    
}

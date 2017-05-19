/**  
 * @Title:  LogAspect.java
 * @Package com.dowin.controller.base
 * @Description: TODO(用一句话描述该文件做什么)
 * @author administrator
 * @date  2016年8月15日 下午1:53:56
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.wang.common.exception.CustomExceptionInterceptor;

/**
 * @ClassName: LogAspect
 * @Description: 日志记录AOP实现
 * @author administrator
 * @date 2016年8月15日 下午1:53:56
 *
 */
@Aspect
public class LogControllerAspect
{
  private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
  
  private String requestPath = null; // 请求地址
  private String inputParamMap = null; // 传入参数
  private Map<String, Object> outputParamMap = null; // 存放输出结果
  private long startTimeMillis = 0; // 开始时间
  private long endTimeMillis = 0; // 结束时间
  private String sessionId = null;// 会话id
  private String methodName = null;// 被请求的方法名

  /**
   * @Title: doBeforeInServiceLayer
   * @Description: 方法调用前触发 记录开始时间
   * @author administrator
   * @param joinPoint
   * @throws
   */
  @Before("execution(* com.*.controller..*.*(..))")
  public void doBeforeInControllerLayer(JoinPoint joinPoint)
  {
    startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间
    Object[] arguments = joinPoint.getArgs();
    if (arguments != null && arguments.length > 0)
    {
      StringBuilder param = new StringBuilder();
      for (Object object : arguments)
      {
        if (object instanceof Serializable)
        {
          param.append(JSON.toJSON(object)+" ");
        }
      }
      inputParamMap = param.toString();
    }
  }

  /**
   * @Title: doAfterInServiceLayer
   * @Description: 方法调用后触发 记录结束时间
   * @author administrator
   * @param joinPoint
   * @throws
   */
  @After("execution(* com.*.controller..*.*(..))")
  public void doAfterInControllerLayer(JoinPoint joinPoint)
  {
    endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间
    this.printAspectLog();
  }

  /**
   * @Title: doAround
   * @Description: 环绕触发
   * @author administrator
   * @param proceedingJoinPoint
   * @return
   * @throws Throwable
   * @throws
   */
  @Around("execution(* com.*.controller..*.*(..))")
  public Object doAround(ProceedingJoinPoint proceedingJoinPoint)
      throws Throwable
  {
    /**
     * 1.获取request信息 2.根据request获取session 3.从session中取出登录用户信息
     */
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    HttpServletRequest request = sra.getRequest();
    // 从session中获取用户信息
    sessionId = request.getSession().getId();
    // 请求的方法名称
    methodName = proceedingJoinPoint.getSignature().getName();
    // 获取请求地址
    requestPath = request.getRequestURI();
    // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
    outputParamMap = new HashMap<String, Object>();
    // result的值就是被拦截方法的返回值
    Object result = proceedingJoinPoint.proceed();
    outputParamMap.put("result", result);
    return result;
  }

  /**
   * @Title: printAspectLog
   * @Description: 输出日志
   * @author administrator
   * @throws
   */
  private void printAspectLog()
  {
    String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
        .format(startTimeMillis);
    String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
        .format(endTimeMillis);
    String headString = "【" + methodName + "】" + "【" + sessionId + "】";
    logger.info(
        headString + "========================>【BEGIN】<=====================");
    logger.info(headString + "【URL(请求地址)】：" + requestPath);
    logger.info(headString + "【sessionId(当前会话id)】：" + sessionId);
    logger.info(headString + "【requestTime(请求时间)】：" + startTime);
    logger.info(headString + "【responseTime(返回时间)】：" + endTime);
    logger.info(headString + "【usedTime(使用时长)】："
        + (endTimeMillis - startTimeMillis) + "ms");
    logger.info(headString + "【requestParam(请求参数)】：" + inputParamMap);
    logger.info(headString + "【responseParam(返回结果)】："
        + JSON.toJSONString(outputParamMap));
    logger.info(
        headString + "========================>【END】<=====================");
  }
}
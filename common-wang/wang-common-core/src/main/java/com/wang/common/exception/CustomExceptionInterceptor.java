/**  
 * @Title:  CustomException.java
 * @Package com.dowin.controller.base
 * @Description: 自定义异常拦截器
 * @author administrator
 * @date  2016年7月18日 下午3:51:17
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.exception;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wang.common.entity.OutResultDto;
import com.wang.common.enums.OutResultEnum.SystemLevelErrorEnum;
import com.wang.common.resource.ResourcesUtils;
import com.wang.common.util.JsonUtils;
import com.wang.common.util.Mail;

/**
 * @ClassName: CustomException
 * @Description: 自定义异常拦截器
 * @author administrator
 * @date 2016年7月18日 下午3:51:17
 *
 */
public class CustomExceptionInterceptor implements HandlerExceptionResolver
{
	private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	
    OutResultDto outResultDto = new OutResultDto();

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
    {
       logger.error(
                "【CustomExceptionInterceptor::resolveException】——自定义异常拦截器，拦截的异常信息是：",
                ex);
//    	if (BizException.class.isAssignableFrom(ex.getClass())) {//如果是业务异常类
//              BizException bizException = (BizException) ex;
//
//              try {
//                  response.setContentType("text/text;charset=UTF-8");
//                  JsonUtils.responseJson(response, bizException.getMsg());
//
//                  Map<String, Object> map = new HashMap<String, Object>();
//                  map.put("errorMsg", bizException.getMsg());//将错误信息传递给view
//                  return new ModelAndView("exception/exception",map);
//              } catch (IOException e) {
//            	  logger.error("系统异常:", e);
//
//              }
//          }
//    	else
//    	{
    		String requestUrl = request.getRequestURL().toString();// 请求地址
            String requestParam = requestParams(request);// 获取请求得参数
            Date date = new Date();
            String smsContent = String.format(
                    "异常拦截信息：【完整地址：%s】-【异常信息：%s】,%tF %tT", requestUrl,
                    ex.getMessage(), date, date);
            String emailContent = String
                    .format("异常拦截信息：【完整地址：%n%s%n】-【请求参数：%s%n】-【异常信息：%s%n】,【当前系统时间：%tF %tT】",
                            requestUrl, requestParam, ex, date, date);
            try
            {
                outResultDto.setCode(SystemLevelErrorEnum.ERROR_40001
                        .toString());
                outResultDto.setMsg(SystemLevelErrorEnum.ERROR_40001
                        .getValue());
                sendEmailException(emailContent);// 发送邮件通知
                sendSmsException(smsContent); // 发送短信通知
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().append(JSON.toJSONString(outResultDto));
                response.getWriter().flush();
                response.getWriter().close();
            }
            catch (Exception e)
            {
                logger.error(
                        "【CustomExceptionInterceptor::resolveException】——自定义异常拦截器:请求地址="
                                + requestUrl + ";;异常信息是：", e);
            }
//    	}
        
        return null;
    }

    /**
     * @Title: requestParams
     * @Description: 获取请求的参数
     * @author administrator
     * @param request
     * @return
     * @throws
     */
    private String requestParams(HttpServletRequest request)
    {
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements())
        {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1)
            {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0)
                {
                    map.put(paramName, paramValue);
                }
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * @Title: sendSmsException
     * @Description: 异常信息发送短信通知
     * @author administrator
     * @param smsContent
     * @throws
     */
    private void sendSmsException(String smsContent)
    {
       /* if (ResourcesUtils.bundle.getString("sms_exception_on_off")
                .equals("on"))
        {
            try
            {
                DowinSmsSend
                        .SmsSend(smsContent, ResourcesUtils.bundle
                                .getString("sms_exception_mobile"));
            }
            catch (UnsupportedEncodingException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/
    }

    /**
     * @Title: sendEmailException
     * @Description:异常信息发送邮件通知
     * @author administrator
     * @param emailContent
     * @throws
     */
    private void sendEmailException(String emailContent)
    {
        if (ResourcesUtils.bundle_web.getString("mail_exception_on_off").equals(
                "on"))
        {
            Mail.sendAndCc(
                    ResourcesUtils.bundle_web.getString("mail_exception_smtp"),
                    ResourcesUtils.bundle_web.getString("mail_exception_from"),
                    ResourcesUtils.bundle_web.getString("mail_exception_to"),
                    ResourcesUtils.bundle_web.getString("mail_exception_copyto"),
                    ResourcesUtils.bundle_web.getString("mail_exception_subject"),
                    emailContent,
                    ResourcesUtils.bundle_web.getString("mail_exception_username"),
                    ResourcesUtils.bundle_web.getString("mail_exception_password"));
        }
    }

    public static void main(String[] args)
    {
        Date date = new Date();
        String message = String.format("异常拦截信息：【完整地址%s】,%tF%n %tT%n",
                "地址", date, date);
        System.out.println(message);
    }
}

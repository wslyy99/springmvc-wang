package com.wang.base;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctc.wstx.util.StringUtil;
import com.wang.common.page.Page;
import com.wang.common.param.ParamData;
import com.wang.common.util.DateUtils;

/**
 * @ClassName: BaseController
 * @Description: 接口层基类
 * @author administrator
 * @date 2016年5月17日 下午2:11:46
 *
 */
public class BaseController
{
	 //protected org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	protected org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	
	private static final String UTF_8 = "utf-8";
	
    /**
     * 前端基础路径
     */
	@Value("${appPath}")
	protected String appPath;
    
    /**
     * 得到ParamData
     */
    public ParamData getParamData()
    {
        return new ParamData(this.getRequest());
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView()
    {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest()
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * @Title: getCookies
     * @Description: 获取cookie
     * @author administrator
     * @return
     * @throws
     */
    public Cookie[] getCookies()
    {
        Cookie[] cookie = getRequest().getCookies();
        return cookie;
    }

    /**
     * 得到Session对象
     */
    public HttpSession getSession()
    {
        HttpSession session = getRequest().getSession();
        return session;
    }


    /**
     * 获取application对象
     * 
     * @return
     */
    public ServletContext getApplication()
    {
        ServletContext application = getRequest().getSession()
                .getServletContext();
        return application;
    }

    /**
     * 获取ip地址
     * 
     * @return
     * @throws UnknownHostException
     */
    public String getIp() throws UnknownHostException
    {
        return InetAddress.getLocalHost().getHostAddress();
    }


    /**
     * @Title: getSessionId
     * @Description: 获取当前会话的SessionId
     * @author administrator
     * @return
     * @throws
     */
    public String getSessionId()
    {
        return getSession().getId();
    }

    /**
     * 得到分页列表的信息
     */
    public Page getPage()
    {

        return new Page();
    }
    
    public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			return resultStr;
		}
	}
	
	/**
	 * 获取请求中的参数值，如果参数值为null刚转为空字符串""
	 * 
	 * @return
	 */
	public Map<String, Object> getParamMap_NullStr(Map map) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Set keys = map.keySet();
		for (Object key : keys) {
			String value = this.getString(key.toString());
			if (value == null){
				value = "";
			}
			parameters.put(key.toString(), value);
		}
		return parameters;
	}
	


	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Integer.parseInt(resultStr);
			} catch (Exception e) {
				logger.error("参数转换错误:",e);
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, null);
	}

	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return BigDecimal.valueOf(Double.parseDouble(resultStr));
			} catch (Exception e) {
				logger.error("参数转换错误:",e);
				return defaultValue;
			}
		}
		return defaultValue;
	}
	
	/**
	 * 根据参数名从HttpRequest中获取String类型的参数值，无值则返回"" .
	 * 
	 * @param key
	 *            .
	 * @return String .
	 */
	public String getString_UrlDecode_UTF8(String key) {
		try {
			String string = getString(key.toString());
			if (Strings.isEmpty(string)){
				return null;
			}else{
				return URLDecoder.decode(this.getString(key), UTF_8);
			}
		} catch (Exception e) {
			logger.error("URL解码错误:",e);
			return "";
		}

	}

    /**
     * 
     * @Title: getIpAddress
     * @Description: 获取Ip
     * @author administrator
     * @param request
     * @return
     * @throws
     */
    public String getIpAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 添加Model消息
     * @param message
     */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
     * 添加Flash消息
     * @param message
     */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
    
    /**
     * 参数绑定异常
     */
//	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
//    public String bindException()
//    {
//        OutResultDto outResultDto = new OutResultDto();
//        outResultDto.setErrorCode("400");
//        outResultDto.setErrorMessage("URI:" + getRequest().getRequestURI()
//                + ";URL:" + getRequest().getRequestURL() + ";QUERYPARAM"
//                + getRequest().getQueryString());
//        return JSON.toJSONString(outResultDto);
//    }
	
	
	/**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
        // Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}

}

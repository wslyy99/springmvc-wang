/**  
 * @Title:  SpringContextUtil.java
 * @Package com.dowin.param
 * @Description: 获取bean的工具类，可用于在线程里面获取bean
 * @author administrator
 * @date  2016年7月1日 上午11:19:33
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.spring;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName: SpringContextUtil
 * @Description: 获取bean的工具类，可用于在线程里面获取bean
 * @author administrator
 * @date 2016年7月1日 上午11:19:33
 *
 */
public class SpringContextUtil implements ApplicationContextAware
{

    private static ApplicationContext context = null;

    /*
     * (non Javadoc)
     * 
     * @Title: setApplicationContext
     * 
     * @Description: spring获取bean工具类
     * 
     * @param applicationContext
     * 
     * @throws BeansException
     * 
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.context = applicationContext;
    }

    public static <T> T getBean(String beanName)
    {
        return (T) context.getBean(beanName);
    }

    public static String getMessage(String key)
    {
        return context.getMessage(key, null, Locale.getDefault());
    }

}

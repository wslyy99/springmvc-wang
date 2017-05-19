/**  
* @Title:  ServerListerner.java
* @Package com.dowin.param
* @Description:监听web应用的生命周期
* @author administrator
* @date  2016年8月24日 下午3:17:35
* @version V1.0  
* Update Logs:
* ****************************************************
* Name:
* Date:
* Description:
******************************************************
*/
package com.wang.common.web.listerner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
* @ClassName: ServerListerner
* @Description: 监听web应用的生命周期
* @author administrator
* @date 2016年8月24日 下午3:17:35
*
*/
public class ServerListerner implements ServletContextListener
{
	private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
    //当Servlet 容器启动Web 应用时调用该方法
    @Override
    public void contextInitialized(ServletContextEvent contextEvent)
    {
        logger.info("=================================");
        logger.info("系统[{"
                + contextEvent.getServletContext().getServletContextName()
                + "}]启动完成!!!");
        logger.info("=================================");
    }

    //当Servlet 容器终止Web应用时调用该方法
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent)
    {
        logger.info("=================================");
        logger.info("系统[{"
                + contextEvent.getServletContext().getServletContextName()
                + "}]已关闭!!!");
        logger.info("=================================");
    }

}

package com.wang.common.resource;

import java.util.ResourceBundle;

/**
 * @ClassName: ResourcesUtils
 * @Description:资源获取工具
 * @author administrator
 * @date 2016年7月5日 下午1:52:41
 *
 */
public class ResourcesUtils
{
	// 加载配置文件conf-web中的信息
	public static final ResourceBundle bundle_web = ResourceBundle
            .getBundle("conf-web");
	
	// 加载配置文件conf-service中的信息
	public static final ResourceBundle bundle_service = ResourceBundle
            .getBundle("conf-service");

}

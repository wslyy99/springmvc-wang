/**  
 * @Title:  ParamManaService.java
 * @Package com.dowin.param
 * @Description: 从zookeeper中取出参数数据并转化成javabean存入内存
 * @author administrator
 * @date  2016年5月23日 下午1:32:40
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.zookeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.wang.common.resource.ResourcesUtils;

/**
 * @ClassName: ParamManaService
 * @Description: 从zookeeper中取出参数数据并转化成javabean存入内存
 * @author administrator
 * @param <T>
 * @date 2016年5月23日 下午1:32:40
 *
 */
public class ParamManaService<T>
{
	private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
    public Map<String, List<T>> cachObject = new HashMap<String, List<T>>();

    /**
   * @Title: ParseZookToBean
   * @Description: 获取zookeeper中的参数数据转换成对应的javabean然后存入内存中
   * @author administrator 2016年5月21日 下午12:21:30
   * @return
   * @throws Exception
   * @throws
   */
    public Map<String, String> ParseZookToBean() throws Exception
    {
    logger.debug("1.从zookeeper中读取参数表名和参数值；2.根据表名字符串获取参数bean对象，并将相应的参数数据存入该对象");
    Map<String, String> mapResult = new HashMap<String, String>();// 返回结果信息
        String paramPath = ResourcesUtils.bundle_service
        .getString("CURATOR_WATCH_PATH");// 参数在zookeeper中的父节点

    Map<String, Object> map = CuratorUtils.getChildren(paramPath);// 从zookeeper中获取参数的节点，即配置文件中table属性的名称
        if (map.containsKey("FAILURE"))
        {
            mapResult.put("FAILURE", map.get("FAILURE").toString());
            logger.debug(map.get("FAILURE").toString());
            return mapResult;
        }
        List<String> paramNodeList = (List<String>) map.get("SUCCESS");
        if (paramNodeList == null)
        {
      mapResult.put("FAILURE", "该节点下没有子节点");
      logger.debug("该节点下没有子节点");
            return mapResult;
        }
    System.out.println("获取到的参数包括：" + paramNodeList.toString());
        ParseZookToBeanProcess(paramNodeList, paramPath);
    mapResult.put("SUCCESS", "解析成功");
        return mapResult;
    }

    /**
   * @Title: ParseZookToBeanProcess
   * @Description: 将参数数据转换成javabean对象并存入内存
   * @author administrator 2016年5月21日 下午2:17:11
   * @param paramNodeList
   *            在zeekeeper中参数的节点
   * @param paramPath
   *            参数在zeekeeper中的路径
   * @throws Exception
   * @throws
   */
    public void ParseZookToBeanProcess(List<String> paramNodeList,
            String paramPath) throws Exception
    {
        Map<String, List<T>> cachObject = new HashMap<String, List<T>>();
        List<T> listObject = new ArrayList<T>();
        String beanPackage = ResourcesUtils.bundle_service
        .getString("CURATOR_BEAN_PACKAGE");// 对应参数表的bean所在包名
    logger
        .debug("遍历参数节点,根据节点名称反射到程序中对应的bean，并分别从zookeeper中取出对应的参数数据赋值给javabean");
        for (String node : paramNodeList)
        {
            Class<?> className = Class.forName(beanPackage.concat(".").concat(
          node));// 通过参数bean所在的包名和bean的名称实例化相应的javabean对象
            cachObject.put(node,
                    (List<T>) ParseBeanGeneric(className, paramPath, node));
        }
        this.cachObject = cachObject;
    }

    /**
   * @Title: ParseBeanGeneric
   * @Description: 读取zookee中的参数数据赋值给对应的javabean
   * @author administrator 2016年5月23日 下午2:47:15
   * @param model
   *            javabean实体类
   * @param paramPath
   *            参数节点路径
   * @param node
   * @return
   * @throws Exception
   * @throws
   */
    public <T> List<T> ParseBeanGeneric(T model, String paramPath, String node)
            throws Exception
    {
        List<T> listObject = new ArrayList<T>();
        String readResult = CuratorUtils.read(
        paramPath.concat("/").concat(node)).get("SUCCESS");// 读取参数数据
    Object[] jsonArray = JSON.parseArray(readResult).toArray();// 转换成JSON数组
    for (int item = 0; item < jsonArray.length; item++)// 遍历JSON数组给javabean赋值
        {
            // System.out.println(jsonArray[item].toString());
            T parseObject = (T) JSON.parseObject(jsonArray[item].toString());
            listObject.add(parseObject);
        }
        return listObject;
    }
}

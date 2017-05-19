/**  
 * @Title:  TempModel.java
 * @Package com.dowin.xml
 * @Description: 读取配置文件中获取参数的条件并存放到该对象中
 * @author administrator
 * @date  2016年5月19日 下午4:25:53
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.zookeeper;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: TempModel
 * @Description: 读取配置文件中获取参数的条件并存放到该对象中
 * @author administrator
 * @date 2016年5月19日 下午4:25:53
 *
 */
public class XmlToBeanDto implements Serializable
{
    private static final long serialVersionUID = -1910982678411962990L;
    private String tableName;// 表名
    private String zkName;// 该表中的参数数据在zookeeper中的节点
    private String mapKeyName;// 获取参数集合时的key值
    private List<String> clumnList;// 要查询的参数列名
    private String whereCondition;// 查询条件
    private String orderbyCondition;// 排序条件

    public String getWhereCondition()
    {
        return whereCondition;
    }

    public void setWhereCondition(String whereCondition)
    {
        this.whereCondition = whereCondition;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getZkName()
    {
        return zkName;
    }

    public void setZkName(String zkName)
    {
        this.zkName = zkName;
    }

    public String getMapKeyName()
    {
        return mapKeyName;
    }

    public void setMapKeyName(String mapKeyName)
    {
        this.mapKeyName = mapKeyName;
    }

    public List<String> getClumnList()
    {
        return clumnList;
    }

    public void setClumnList(List<String> clumnList)
    {
        this.clumnList = clumnList;
    }

    /**
     * @return orderbyCondition
     */
    public String getOrderbyCondition()
    {
        return orderbyCondition;
    }

    /**
     * @param orderbyCondition
     *            要设置的 orderbyCondition
     */
    public void setOrderbyCondition(String orderbyCondition)
    {
        this.orderbyCondition = orderbyCondition;
    }

}

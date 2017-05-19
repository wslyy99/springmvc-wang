/**  
 * @Title:  ParamXmlParseUtil.java
 * @Package com.dowin.utils.zookeeper
 * @Description: 解析参数配置文件
 * @author administrator
 * @date  2016年5月19日 下午8:33:05
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.zookeeper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;


/**
 * @ClassName: ParamXmlParseUtil
 * @Description: 解析参数配置文件
 * @author administrator
 * @date 2016年5月19日 下午8:33:05
 *
 */
public class ParamXmlParseUtil
{
	private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(ParamXmlParseUtil.class);

    /**
     * @Title: XmlToZookeeper
     * @Description: 从xml配置文件中读取获取参数的内容和信息，然后从数据库读取参数数据并存入zookeeper中
     * @author administrator 2016年5月19日 下午8:47:23
     * @param path
     *            配置未见路径
     * @return
     * @throws Exception
     * @throws
     */
    public static List<XmlToBeanDto> XmlToZookeeper() throws Exception
    {
        URL path = ParamXmlParseUtil.class.getClassLoader().getResource(
                "params-config.xml");// ClassLoader.getSystemResource("params-config.xml");
        if (StringUtils.isEmpty(path))
        {
            logger.info("params-config.xml不存在");
            return null;
        }
        logger.info("根据文件路径读取配置文件中的内容，然后读取不同节点的数据，存入对象XmlToBeanDto中");
        SAXReader saxReader = new SAXReader();
        System.out.println("saxReader");
        File file = new File(path.toURI());
        System.out.println("file是否存在：" + file.exists());
        Document document = saxReader.read(file);
        if (document == null)
        {
            logger.info("document为null请检查params-config.xml文件是否存在");
            return null;
        }
        Element elements = document.getRootElement();
        List<XmlToBeanDto> table = new ArrayList<XmlToBeanDto>();
        for (Element element : elements.elements("para"))
        {
            XmlToBeanDto tableModel = new XmlToBeanDto();
            if (element.getName().equals("para"))
            {
                tableModel.setTableName(element.attribute("table").getText());
                tableModel.setZkName(element.attribute("para_name").getText());
            }
            tableModel.setMapKeyName(element.element("key_column")
                    .getTextTrim());
            List<String> columnList = new ArrayList<String>();
            for (Element subElement : element.elements("value_column"))
            {
                columnList.add(subElement.element("column_name").getText()
                        .concat(" as ")
                        .concat(subElement.element("column_alias").getText()));
            }
            for (Element sub2Element : element.elements("condition_column"))
            {
                tableModel.setWhereCondition(sub2Element.element("column_name")
                        .getTextTrim().concat("=")
                        .concat(sub2Element.element("value").getTextTrim()));
            }
            if (element.getName().equals("order_column"))
            {
                for (Element sub3Element : element.elements("order_column"))
                {
                    tableModel.setOrderbyCondition(sub3Element.element(
                            "column_name").getTextTrim());
                }
            }
            tableModel.setClumnList(columnList);
            table.add(tableModel);
        }
        System.out.println("XmlToZookeeper()执行完毕");
        return table;
    }

    /**
     * @Title: GetNodeFromXml
     * @Description: 获取table和key_column的对应关系
     * @author administrator 2016年5月21日 下午3:42:14
     * @return
     * @throws Exception
     * @throws
     */
    public static Map<String, String> GetNodeFromXml() throws Exception
    {
        URL path = ParamXmlParseUtil.class.getClassLoader().getResource(
                "params-config.xml");

        if (StringUtils.isEmpty(path))
        {
            logger.info("params-config.xml不存在");
            return null;
        }
        SAXReader saxReader = new SAXReader();
        System.out.println("saxReader");
        File file = new File(path.toURI());
        System.out.println("file是否存在：" + file.exists());
        Document document = saxReader.read(file);
        if (document == null)
        {
            logger.info("document为null请检查params-config.xml文件是否存在");
            return null;
        }
        Element elements = document.getRootElement();
        Map<String, String> result = new HashMap<String, String>();
        for (Element element : elements.elements("para"))
        {
            result.put(element.attribute("para_name").getText(), element
                    .element("key_column").getTextTrim());
        }
        System.out.println("GetNodeFromXml()执行完毕");
        return result;
    }

    public static void main(String[] args) throws IOException
    {
        try
        {
            // URL pathUrl =
            // ParamXmlParseUtil.class.getClassLoader().getResource(
            // "params-config.xml");
            //
            // if (StringUtils.isEmpty(pathUrl))
            // {
            // logger.info("params-config.xml不存在");
            // }
            // System.out
            // .println(JSON.toJSONString(CuratorUtils.getChildren("/")));
            System.out.println(CuratorUtils.delete("/").get("SUCCESS"));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

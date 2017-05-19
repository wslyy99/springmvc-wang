package com.wang.common.zookeeper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;

import com.ctc.wstx.util.StringUtil;
import com.wang.common.resource.ResourcesUtils;

/**
 * @ClassName: CuratorTools
 * @Description: curator操作zookeeper
 * @author administrator
 * @date 2016年5月18日 下午2:17:34
 *
 */
public class CuratorUtils
{

    static CuratorFramework zkclient = CuratorClientFactory.getInstance();

    /**
     * @Title: create
     * @Description: 创建子节点
     * @author administrator 2016年5月18日 下午8:08:57
     * @param parentPath
     *            父节点路径
     * @param childName
     *            子节点名称
     * @param content
     *            子节点内容
     * @throws Exception
     * @throws
     */
    public static Map<String, String> createChild(String parentPath,
            String childName, String childContent) throws Exception
    {
        if (parentPath.isEmpty())// 如果父节点为空,从配置文件中读取CURATOR_WATCH_PATH路径作为父节点
        {
            parentPath = ResourcesUtils.bundle_service.getString("CURATOR_WATCH_PATH");
            createParent(parentPath, "");
        }
        String childPath = ZKPaths.makePath(parentPath, childName);// 在指定父节点下创建子节点
        Map<String, String> resultMap = new HashMap<String, String>();
        if (zkclient.checkExists().forPath(childPath) == null)
        {
            zkclient.create().withMode(CreateMode.PERSISTENT)
                    .forPath(childPath, childContent.getBytes("utf-8"));
            resultMap.put("SUCCESS", "添加成功");
            System.out.println("添加成功！！！");
        }
        else
        {
            resultMap.put("FAILURE", "[createChild]zookeeper已存在该节点");
        }
        return resultMap;
    }

    /**
     * @Title: createParent
     * @Description: 创建父节点
     * @author administrator 2016年5月20日 上午11:17:33
     * @param parentPath
     *            父节点路径
     * @param content
     *            父节点数据
     * @return
     * @throws Exception
     * @throws
     */
    public static Map<String, Object> createParent(String parentPath,
            String content) throws Exception
    {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (zkclient.checkExists().forPath(parentPath) == null)
        {
            zkclient.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(parentPath,
                            new String(content.getBytes(), "utf-8").getBytes());
            resultMap.put("SUCCESS", "添加成功");
            System.out.println("添加成功！！！");
        }
        else
        {
            resultMap.put("FAILURE", "[createParent]zookeeper中已存在该节点");
        }
        return resultMap;
    }

    /**
     * @Title: delete
     * @Description: 删除节点
     * @author administrator 2016年5月18日 下午8:10:03
     * @param path
     *            删除节点的路径
     * @throws Exception
     * @throws
     */
    public static Map<String, Object> delete(String path) throws Exception
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (zkclient.checkExists().forPath(path) != null)
        {
            zkclient.delete().guaranteed().deletingChildrenIfNeeded()
                    .forPath(path);
            resultMap.put("SUCCESS", "删除成功");
            System.out.println("删除成功!");
        }
        else
        {
            resultMap.put("FAILURE", "[delete]zookeeper中不存在该节点");
        }
        return resultMap;
    }

    /**
     * @Title: read
     * @Description: 读取数据
     * @author administrator 2016年5月18日 下午8:34:18
     * @param path
     *            读取的路径
     * @return
     * @throws Exception
     * @throws
     */
    public static Map<String, String> read(String path) throws Exception
    {
        Map<String, String> resultMap = new HashMap<String, String>();
        String data = null;
        if (zkclient.checkExists().forPath(path) != null)
        {
            data = new String(zkclient.getData().forPath(path), "utf-8");
            resultMap.put("SUCCESS", data);
        }
        else
        {
            resultMap.put("FAILURE", "[read]zookeeper中不存在该节点");
        }
        System.out.println("读取的数据:" + data);
        return resultMap;
    }

    /**
     * @Title: upload
     * @Description: 上传文件
     * @author administrator 2016年5月18日 下午8:35:05
     * @param zkPath
     *            上传路径
     * @param localpath
     *            本地文件路径
     * @throws Exception
     * @throws
     */
    public static Map<String, Object> upload(String zkPath, String localpath)
            throws Exception
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtil.isAllWhitespace(zkPath))
        {
            resultMap.put("FAILURE", "zookeeper中不存在该节点");
            return resultMap;
        }
        if (StringUtil.isAllWhitespace(localpath))
        {
            resultMap.put("FAILURE", "本地文件路径不存在");
            return resultMap;
        }
        if (zkclient.checkExists().forPath(zkPath) == null)
        {
            createParent(zkPath, "");// 创建路径
        }
        byte[] bs = FileUtils.readFileToByteArray(new File(localpath));
        zkclient.setData().forPath(zkPath, bs);
        resultMap.put("SUCCESS", "上传文件成功");
        System.out.println("上传文件成功！");
        return resultMap;
    }

    /**
     * @Title: getChildren
     * @Description: 获取子节点
     * @author administrator 2016年5月20日 下午8:47:06
     * @param path
     * @return
     * @throws Exception
     * @throws
     */
    public static Map<String, Object> getChildren(String path) throws Exception
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (path.isEmpty())
        {
            resultMap.put("FAILURE", "节点为空");
            return resultMap;
        }
        if (zkclient.checkExists().forPath(path) == null)
        {
            resultMap.put("FAILURE", path.concat("节点不存在"));
            return resultMap;
        }
        List<String> childList = zkclient.getChildren().forPath(path);
        resultMap.put("SUCCESS", childList);
        return resultMap;
    }

    public static void main(String[] args) throws Exception
    {
        // /DOWIN/ZK/PARAM

        System.out.println(delete("/").get("SUCCESS"));
    }
}
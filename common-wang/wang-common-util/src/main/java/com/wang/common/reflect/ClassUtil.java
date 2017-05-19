/**  
 * @Title:  ClassUtil.java
 * @Package com.dowin.utils.common
 * @Description: TODO(用一句话描述该文件做什么)
 * @author administrator
 * @date  2016年5月21日 下午3:52:46
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.reflect;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: ClassUtil
 * @Description: 类相关的工具类
 * @author administrator
 * @date 2016年5月21日 下午3:52:46
 *
 */

public class ClassUtil
{
    /**
     * 从包下获取所有类名（带包名）
     * 
     * @param packageName
     *            包名
     * @return 包下的所有类名
     */
    public static List<String> getClassNameByPackage(String packageName)
    {
        return getClassName(packageName, true);
    }

    /**
     * 从包下获取所有类名(不带包名)
     * 
     * @param packageName
     *            包名
     * @return 包下的所有类名
     */
    public static List<String> getClassNameNoPackage(String packageName)
    {
        List<String> list = getClassName(packageName, true);
        List<String> newList = new ArrayList<String>();
        for (String m : list)
        {
            newList.add(m.replace(packageName.concat("."), ""));
        }
        return newList;
    }

    /**
     * 从包下获取包含指定注解的所有类名
     * 
     * @param packageName
     *            包名
     * @param annotation
     *            注解类数组
     * @return 包下的所有类名
     */
    public static List<String> getClassNameByAnnotation(String packageName,
            Class<? extends Annotation>... annotation)
    {
        List<String> classNames = getClassName(packageName, true);
        if (classNames != null)
        {
            Iterator<String> it = classNames.iterator();
            while (it.hasNext())
            {
                String className = it.next();
                try
                {
                    Class<?> clasz = Class.forName(className);
                    boolean save = false;
                    for (Class<? extends Annotation> ann : annotation)
                    {
                        if (clasz.isAnnotationPresent(ann))
                        {
                            save = true;
                        }
                    }
                    if (!save)
                    {
                        it.remove();
                    }

                }
                catch (ClassNotFoundException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return classNames;
    }

    /**
     * 从包下获取所有类名(供类内使用)
     * 
     * @param packageName
     *            包名
     * @param childPackage
     *            是否遍历子包
     * @return 包下的所有类名
     */
    @SuppressWarnings("deprecation")
    private static List<String> getClassName(String packageName,
            boolean childPackage)
    {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null)
        {
            String type = url.getProtocol();
            if (type.equals("file"))
            {
                fileNames = getClassNameByFile(packageName,
                        URLDecoder.decode(url.getPath()), null, childPackage);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类(供类内使用)
     * 
     * @param filePath
     *            文件路径
     * @param className
     *            类名集合
     * @param childPackage
     *            是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String packageName,
            String filePath, List<String> className, boolean childPackage)
    {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath.substring(1));
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles)
        {
            if (childFile.isDirectory())
            {
                if (childPackage)
                {
                    myClassName.addAll(getClassNameByFile(packageName,
                            childFile.getPath(), myClassName, childPackage));
                }
            }
            else
            {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class"))
                {
                    childFilePath = childFilePath.substring(0,
                            childFilePath.length() - 6);
                    int lastIndex = childFilePath.lastIndexOf('\\');
                    if (lastIndex == -1)
                    {

                        myClassName.add(packageName + "." + childFilePath);
                    }
                    else
                    {
                        myClassName.add(packageName + "."
                                + childFilePath.substring(lastIndex + 1));
                    }
                }
            }
        }

        return myClassName;
    }

}

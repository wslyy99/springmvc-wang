package com.wang.valid.enums;

import com.wang.common.enums.IEnum;

/**
 * @ClassName: BusinessLevelErrorEnum
 * @Description: 业务级别错误码枚举
 * @author administrator
 * @date 2016年4月29日 上午9:24:02
 */
public enum ValidLevelErrorEnum implements IEnum
{
    ERROR_10001("注册信息为空", 10001), ERROR_10002("手机号码为空", 10002);
    
    // 成员变量,ERROR_10088( "没有与该页面实例相匹配的元素", 10088)
    private String name;
    private int index;

    // 构造方法
    private ValidLevelErrorEnum(String name, int index)
    {
        this.name = name;
        this.index = index;
    }

    // 覆盖默认方法
    @Override
    public String toString()
    {
        return String.valueOf(this.index);
    }

    /**
     * @Title: getName
     * @Description:枚举英文标识
     * @author administrator
     * @return
     * @throws
     */
    @Override
    public String getName()
    {
        return this.name();
    }

    /**
     * @Title: getName
     * @Description:枚举中的中文描述值
     * @author administrator
     * @return
     * @throws
     */
    @Override
    public String getValue()
    {
        return this.name;
    }

    /**
     * @Title: getName
     * @Description:枚举中的整形索引值
     * @author administrator
     * @return
     * @throws
     */
    @Override
    public int getIndex()
    {
        return this.index;
    }
}
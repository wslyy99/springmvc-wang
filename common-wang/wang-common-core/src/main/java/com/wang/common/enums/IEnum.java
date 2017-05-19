package com.wang.common.enums;

/**
 * @ClassName: IEnum
 * @Description: 枚举接口
 * @author administrator
 * @date 2016年4月29日 上午10:02:00
 *
 */
public interface IEnum
{
    /**
     * @Title: getName
     * @Description:当前枚举类中的唯一英文标识
     * @author administrator
     * @return
     * @throws
     */
    public String getName();

    /**
     * @Title: getValue
     * @Description:枚举中的中文描述值
     * @author administrator
     * @return
     * @throws
     */
    public String getValue();

    /**
     * @Title: getValue
     * @Description:枚举中整形索引值
     * @author administrator
     * @return
     * @throws
     */
    public int getIndex();

}

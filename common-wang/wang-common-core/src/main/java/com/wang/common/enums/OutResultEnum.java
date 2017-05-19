/**  
 * @Title:  OutResultEnum.java
 * @Package com.dowin.entity.common
 * @Description: 输出结果相关枚举
 * @author administrator
 * @date  2016年4月28日 下午8:32:55
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.enums;

/**
 * @ClassName: OutResultEnum
 * @Description: 输出结果相关枚举类
 * @author administrator
 * @date 2016年4月28日 下午8:32:55
 *
 */
public class OutResultEnum
{

    /**
     * @ClassName: OutStatusEnum
     * @Description: 输出结果状态枚举
     * @author administrator
     * @date 2016年4月28日 下午8:35:42
     *
     */
    public enum OutStatusEnum implements IEnum
    {
        SUCCEED("成功", 1), FAILURE("失败", 0);

        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private OutStatusEnum(String name, int index)
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
         * @Title: getName @Description:枚举英文标识 @author
         *         administrator @return @throws
         */
        @Override
        public String getName()
        {
            return this.name();
        }

        /**
         * @Title: getName @Description:枚举中的中文描述值 @author
         *         administrator @return @throws
         */
        @Override
        public String getValue()
        {
            return this.name;
        }

        /**
         * @Title: getName @Description:枚举中的整形索引值 @author
         *         administrator @return @throws
         */
        @Override
        public int getIndex()
        {
            return this.index;
        }

    }


    /**
     * @ClassName: SystemLevelErrorEnum
     * @Description: 系统级别错误码枚举
     * @author administrator
     * @date 2016年4月29日 上午9:25:33
     *
     */
    public enum SystemLevelErrorEnum implements IEnum
    {
        ERROR_40001("系统异常", 40001), ERROR_40002("无匹配的数据",
                40002), ERROR_40003("未登录", 40003), ERROR_40004("退出登录失败", 40004), ERROR_40005("系统超时", 40005);

        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private SystemLevelErrorEnum(String name, int index)
        {
            this.name = name;
            this.index = index;
        }

        // 覆盖默认方法，以字符串的形式返回枚举索引值
        @Override
        public String toString()
        {
            return String.valueOf(this.index);
        }

        /**
         * @Title: getName @Description:枚举英文标识 @author
         *         administrator @return @throws
         */
        @Override
        public String getName()
        {
            return this.name();
        }

        /**
         * @Title: getName @Description:枚举中的中文描述值 @author
         *         administrator @return @throws
         */
        @Override
        public String getValue()
        {
            return this.name;
        }

        /**
         * @Title: getName @Description:枚举中的整形索引值 @author
         *         administrator @return @throws
         */
        @Override
        public int getIndex()
        {
            return this.index;
        }
    }

    public static void main(String[] args)
    {
        // System.out.println("业务级别错误编码枚举类测试如下：");
        // System.out.println("字符串格式索引值：" + BusinessLevelErrorEnum.ERROR_10001);
        // System.out.println("唯一英文枚举标识："
        // + BusinessLevelErrorEnum.ERROR_10001.getName());
        // System.out.println("枚举描述值："
        // + BusinessLevelErrorEnum.ERROR_10001.getValue());
        // System.out.println("整形格式索引值："
        // + BusinessLevelErrorEnum.ERROR_10001.getIndex());
        //
        // System.out.println("输出结果枚举测试如下：");
        // System.out.println("字符串格式索引值：" + OutStatusEnum.SUCCEED);
        // System.out.println("唯一英文枚举标识：" + OutStatusEnum.SUCCEED.getName());
        // System.out.println("枚举描述值：" + OutStatusEnum.SUCCEED.getValue());
        // System.out.println("整形格式索引值：" + OutStatusEnum.SUCCEED.getIndex());
        //
        // System.out.println("系统级别错误编码枚举测试如下：");
        // System.out.println(SystemLevelErrorEnum.ERROR_40001);
        // System.out.println(SystemLevelErrorEnum.ERROR_40001.getName());
        // System.out.println(SystemLevelErrorEnum.ERROR_40001.getValue());

        System.out.println(OutStatusEnum.FAILURE.toString());
        // String count = "0";
        // Preconditions.checkArgument(count != "0", "can't not null: %s",
        // count);

    }
}

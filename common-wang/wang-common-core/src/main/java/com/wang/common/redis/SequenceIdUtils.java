/**  
 * @Title:  生成有顺序的序列ID并存放到Redis
 * @Package com.dowin.utils.common
 * @Description: 根据业务生成有顺序的序列ID并存放到Redis
 * @author administrator
 * @date  2016年4月22日 下午4:55:03
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.redis;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.wang.common.util.DateUtils;

/**
 * @ClassName: SequenceIdUtils
 * @Description: 自增序列生成工具
 * @author administrator
 * @date 2016年4月22日 下午4:55:03
 *
 */
public class SequenceIdUtils
{
    static Map<String, String> tableMap = InitializeTableKeys();
    public static String invokerTypeFlag = null;
    private static int SequenceIdExpireSecond = 24 * 3600;// 自增序列有效期时间24小时

    /**
     * @Title: 产生有序的ID编号
     * @Description: 根据key值生成有序ID
     * @author administrator
     * @return
     * @throws
     */
    public synchronized static String GenerateIdByCode(String key)
    {
        String result = "";
        String flag = "";
        if (tableMap.containsKey(key.trim()))
        {
            flag = tableMap.get(key.trim());
        }
        switch (flag)
        {
        case "5":
            result = GetGeneralSequence(key, "10000");// 5位自增序列
            break;
        case "6":
            result = GetGeneralSequence(key, "100000");// 6位自增序列
            break;
        case "7":
            result = GetGeneralSequence(key, "1000000");// 7位自增序列
            break;
        case "8":
            result = GetGeneralSequence(key, "10000000");// 8位自增序列
            break;
        case "10":
            result = GetGeneralSequence(key, "1000000000");// 10位自增序列
            break;
        case "18":
            result = GetEighteenSequence(key);// 18位自增序列
            break;
        }
        return result;
    }

    /**
     * @Title:GetEighteenSequence
     * @Description: 产生18位序列(当前日期与有序序列的组合)
     * @author administrator
     * @param key
     * @return
     * @throws
     */
    private static String GetEighteenSequence(String key)
    {
        String result = "";
        try
        {
            String oldId = RedisUtils.get(key);
            String newId = "1";
            String currentDate = DateUtils.getDate("yyyyMMdd");
            if (oldId != null && oldId != "")
            {
                Integer newSeedValue = Integer.parseInt(oldId) + 1;
                result = currentDate
                        .concat(String.format("%010d", newSeedValue));
                RedisUtils.set(key, String.valueOf(newSeedValue),
                        SequenceIdExpireSecond);
            }
            else
            {
                RedisUtils.set(key, newId, SequenceIdExpireSecond);
                result = currentDate
                        .concat(String.format("%010d", Integer.valueOf(newId)));
            }
        }
        catch (Exception e)
        {
            System.out.println("----------生成的18位有序序列出现异常是：" + e);
        }
        return result;
    }

    /**
     * @Title: 产生普通序列
     * @Description: 10位、8位、6位等普通自增序列
     * @author administrator
     * @param key
     * @param seedValue种子值
     * @return
     * @throws
     */
    private static String GetGeneralSequence(String key, String seedValue)
    {
        String oldId = RedisUtils.get(key);
        String newId = seedValue;
        try
        {
            if (oldId != null && oldId != "")
            {
                newId = String.valueOf(Integer.parseInt(oldId) + 1);
                RedisUtils.set(key, newId);
            }
            else
            {
                RedisUtils.set(key, newId);
            }
        }
        catch (Exception e)
        {
            System.out.println("----------GetGeneralSequence出现异常是：" + e);
        }
        return newId;
    }

    /**
     * @return
     * @Title: 初始化各个表对应的唯一key值
     * @Description: 初始化各个表对应的唯一key值
     * @author administrator
     * @throws
     */
    private static Map<String, String> InitializeTableKeys()
    {
        Map<String, String> map = new HashMap<String, String>();
        for (TablesEnum s : EnumSet.allOf(TablesEnum.class))
        {
            map.put(s.getValue(), s.toString());
        }
        return map;
    }

    /**
     * @ClassName: TableEnum
     * @Description: 需要产生有序序列编号的数据表的枚举
     * @author administrator
     * @date 2016年5月3日 下午1:34:13
     *
     */
    public enum TablesEnum
    {
        info_developer("info_developer:developer_id", 5), info_agent_company(
                "info_agent_company:agent_company_id", 6), info_house_project(
                "info_house_project:house_project_id", 8), log_buy_house_attachment(
                "log_buy_house_attachment:attachment_id", 10), log_score_access(
                "log_score_access:access_id", 18);
    	


        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private TablesEnum(String name, int index)
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

        public int getIndex()
        {
            return this.index;
        }
    }

    /**
     * @ClassName: InvokerType
     * @Description: 调用者类型（controller：接口层；service：服务层）
     * @author administrator
     * @date 2016年6月1日 下午3:37:30
     *
     */
    public enum InvokerType
    {
        controller("redis.properties", 1), service("/META-INF/redis.properties",
                2);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private InvokerType(String name, int index)
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

        public int getIndex()
        {
            return this.index;
        }
    }

    public static void main(String[] args)
    {
        // Jedis jedis = RedisUtils.getJedis();
        // jedis.set("log_background_info:rec_id", "1");

        // System.out.println("info_cust: cust_id:"
        // + GenerateIdByCode("info_cust: cust_id"));
        // System.out.println("trade_showings_order: showings_order_id:"
        // + GenerateIdByCode("trade_showings_order: showings_order_id"));
        // System.out.println("12125");
        // for (int i = 0; i < 10; i++)
        // {
        // System.out.println("log_background_info:rec_id:"
        // + GenerateIdByCode("log_background_info:rec_id"));
        // }

        // Map<String, String> tableMap = InitializeTableKeys();
        // if (tableMap.containsKey(TableEnum.info_agent.getValue()))
        // {
        // System.out.println(tableMap.get(TableEnum.info_agent.getValue()));
        // }
        // String value = SequenceIdUtils
        // .GenerateIdByCode(TablesEnum.log_background_info_id.getValue());
         String value = SequenceIdUtils
         .GenerateIdByCode(TablesEnum.info_agent_company
         .getValue());
         System.out.println(value);

    }
}

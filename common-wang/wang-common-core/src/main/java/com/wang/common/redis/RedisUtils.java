/**  
 * @Title:  RedisUtils.java
 * @Package com.wechat.servlets
 * @Description: redis操作工具类
 * @author administrator
 * @date  2016年5月12日 下午5:53:55
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.redis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.wang.common.resource.PropertiesUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName: RedisUtils
 * @Description: redis操作工具类
 * @author administrator
 * @date 2016年5月12日 下午5:53:55
 *
 */
public class RedisUtils
{
    private static JedisPool pool = null;
    private static ThreadLocal<JedisPool> poolThreadLocal = new ThreadLocal<JedisPool>();

    // private static final String propertityFullName = "redis.properties";

    /**
     * @Title: getPool
     * @Description: 构建redis连接池
     * @author administrator 2016年5月13日 上午11:08:41
     * @return
     * @throws
     */
    public static JedisPool getPool(String propertityName)
    {
        try
        {
            if (pool == null)
            {
                PropertiesUtil propUtil = null;
                // if (Strings.isNullOrEmpty(propertityName))
                // {
                // propUtil = new PropertiesUtil();
                // }
                // else
                // {
                // propUtil = new PropertiesUtil(propertityFullName);
                // }
                propUtil = new PropertiesUtil();
                int PORT = Integer.valueOf(propUtil.readValue("redis.port"));// 端口
                String IP = propUtil.readValue("redis.ip");
                int MAX_ACTIVE = Integer.valueOf(propUtil
                        .readValue("redis.pool.maxActive"));// 最大连接数
                int MAX_IDLE = Integer.valueOf(propUtil
                        .readValue("redis.pool.maxIdle"));// 最大空闲连接数
                int MAX_WAIT = Integer.valueOf(propUtil
                        .readValue("redis.pool.maxWait"));// 最大等待时间
                int TIMEOUT = Integer.valueOf(propUtil
                        .readValue("redis.pool.timeout"));// 连接超时时间
                boolean TEST_ON_BORROW = Boolean.valueOf(propUtil
                        .readValue("redis.pool.testOnBorrow"));
                String reids_pwd = propUtil.readValue("redis.pwd");// 密码

                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWaitMillis(MAX_WAIT);
                config.setTestOnBorrow(TEST_ON_BORROW);
                pool = new JedisPool(config, IP, PORT, TIMEOUT, reids_pwd);
                System.out.println(pool.isClosed());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return pool;
    }

    /**
     * @Title: getConnection
     * @Description: 
     *               如果poolThreadLocal没有本线程对应的JedisPool创建一个新的JedisPool，将其保存到线程本地变量中
     * @author administrator 2016年5月12日 下午8:58:34
     * @return
     * @throws
     */
    public static JedisPool getConnection()
    {
        if (poolThreadLocal.get() == null)
        {
            pool = RedisUtils.getPool(null);
            poolThreadLocal.set(pool);
            return pool;
        }
        else
        {
            return poolThreadLocal.get();// 直接返回线程本地变量
        }
    }

    /**
     * @Title: getConnection
     * @Description: 
     *               如果poolThreadLocal没有本线程对应的JedisPool创建一个新的JedisPool，将其保存到线程本地变量中
     * @author administrator 2016年5月12日 下午8:58:34
     * @return
     * @throws
     */
    public static JedisPool getConnection(String propertyName)
    {
        if (poolThreadLocal.get() == null)
        {
            pool = RedisUtils.getPool(propertyName);
            poolThreadLocal.set(pool);
            return pool;
        }
        else
        {
            return poolThreadLocal.get();// 直接返回线程本地变量
        }
    }

    /**
     * @Title: returnResource
     * @Description: 返还到连接池
     * @author administrator 2016年5月13日 上午9:27:27
     * @param pool
     * @param redis
     * @throws
     */
    public static void returnResource(Jedis redis)
    {
        try
        {
            // 1. 请求服务端关闭连接
            redis.quit();
        }
        catch (Exception e)
        {
            System.out.println("quit jedis connection for server fail: " + e);
        }

        try
        {
            if (redis.isConnected())
            {
                // 2. 客户端主动关闭连接
                redis.disconnect();
            }
        }
        catch (Exception e)
        {
            System.out.println("disconnect jedis connection fail: " + e);
        }
        finally
        {
            redis.close();
        }
    }

    /**
     * @Title: get
     * @Description: 获取数据
     * @author administrator 2016年5月13日 上午9:27:49
     * @param key
     * @return
     * @throws
     */
    public static String get(String key)
    {
        System.out.println("获取数据get:" + key);
        String value = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            System.out.println("连接池是否关闭：" + pool.isClosed());
            jedis = pool.getResource();
            value = jedis.get(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * @Title: del
     * @Description: 删除数据
     * @author administrator 2016年5月13日 上午9:28:46
     * @param key
     * @return
     * @throws
     */
    public static Long del(String key)
    {
        Long value = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            value = jedis.del(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource(jedis);
        }
        return value;
    }

    /**
     * @Title: exists
     * @Description: 判断是否存在
     * @author administrator 2016年5月13日 上午9:29:38
     * @param key
     * @return
     * @throws
     */
    public static Boolean exists(String key)
    {
        Boolean value = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            value = jedis.exists(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource(jedis);
        }
        return value;
    }

    /**
     * @Title: set
     * @Description: 添加数据并设置过期时间
     * @author administrator 2016年5月13日 上午9:30:28
     * @param key
     * @param value
     * @param expireSeconds
     *            (过期时间，秒)
     * @return
     * @throws
     */
    public static Long set(String key, String value, int expireSeconds)
    {
        Long result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            jedis.set(key, value);
            result = jedis.expire(key, expireSeconds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: expire
     * @Description: 设置过期时间
     * @author administrator 2016年5月13日 上午9:31:23
     * @param key
     * @param expireSeconds
     *            (过期时间，秒)
     * @return
     * @throws
     */
    public static Long expire(String key, int expireSeconds)
    {
        Long result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.expire(key, expireSeconds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: set
     * @Description: 赋值数据
     * @author administrator 2016年5月13日 上午9:31:57
     * @param key
     * @param value
     * @return
     * @throws
     */
    public static String set(String key, String value)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.set(key, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: hmset
     * @Description: 保存hashmap格式的数据
     * @author administrator 2016年6月8日 下午5:18:44
     * @param key
     * @param hash
     * @return
     * @throws
     */
    public static String hmset(String key, Map<String, String> hash)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.hmset(key, hash);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: hmset
     * @Description: 保存hashmap格式的数据并设置过期时间
     * @author administrator 2016年6月8日 下午5:18:44
     * @param key
     * @param hash
     * @param expireSeconds
     *            过期时间(秒)
     * @return
     * @throws
     */
    public static String hmset(String key, Map<String, String> hash,
            int expireSeconds)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.hmset(key, hash);
            jedis.expire(key, expireSeconds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: hsetnx
     * @Description: 向map中添加字段
     * @author administrator 2016年6月8日 下午5:56:15
     * @param key
     *            索引key值
     * @param field
     *            添加的新字段
     * @param value
     *            新字段的值
     * @return
     * @throws
     */
    public static Long hsetnx(String key, String field, String value)
    {
        Long result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.hsetnx(key, field, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: hmset
     * @Description: 保存hashmap格式的数据
     * @author administrator 2016年6月8日 下午5:18:44
     * @param key
     * @param hash
     * @return
     * @throws
     */
    public static Map<String, String> hmget(String key)
    {
        Map<String, String> result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            if (jedis.exists(key))
            {
                result = jedis.hgetAll(key);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }

    /**
    * @Title: isExist
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author administrator
    * @param key
    * @return
    * @throws
    */
    public static Boolean isExist(String key)
    {
        Boolean result = false;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.exists(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: hexists
     * @Description:判断hashmap中是否存在某个字段
     * @author administrator 2016年6月8日 下午6:03:13
     * @param key
     *            索引值
     * @param field
     *            判断的字段名称
     * @return
     * @throws
     */
    public static Boolean hexists(String key, String field)
    {
        Boolean result = false;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.hexists(key, field);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: sadd
     * @Description: 赋值数据
     * @author administrator 2016年5月13日 上午9:32:45
     * @param key
     * @param value
     * @return
     * @throws
     */
    public static Long sadd(String key, String value)
    {
        Long result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.sadd(key, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: sismember
     * @Description: 判断set中是否有值
     * @author administrator 2016年5月13日 上午9:33:19
     * @param key
     * @param member
     * @return
     * @throws
     */
    public static Boolean sismember(String key, String member)
    {
        Boolean result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.sismember(key, member);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource(jedis);
        }
        return result;
    }

    /**
     * Redis lpush/rpush 命令将一个或多个值插入到列表头部/尾部。 如果 key 不存在，一个空列表会被创建并执行
     * lpush/rpush 操作。 当 key 存在但不是列表类型时，返回一个错误
     * 
     * @param Direction
     *            direction 队列头部或尾部
     * @param String
     *            key reids键名
     * @param String
     *            value 键值
     */
    public static Boolean pushBlocking(final Direction direction,
            final String key, final String value)
    {
        JedisPool pool = null;
        Jedis jedis = null;
        Boolean result = false;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            if (direction == Direction.Left)
            {
                result = jedis.lpush(key, value) > 0;// 队列左侧插入数据
            }
            else
            {
                result = jedis.rpush(key, value) > 0;// 队列右侧插入数据
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource(jedis);
        }
        return result;
    }

    /**
     * @Title: popBlocking
     * @Description:Redis Blpop 命令移出并获取列表的元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @author administrator 2016年6月20日 上午10:52:04
     * @param direction
     *            队列的头部或尾部
     * @param timeout
     *            超时
     * @param key
     *            键名
     * @return 如果列表为空，返回一个 nil 。 否则，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key
     *         ，第二个元素是被弹出元素的值。
     * @throws
     */
    public static List<String> popBlocking(final Direction direction,
            final String key, final int timeout)
    {
        List<String> resultList = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            if (direction == Direction.Left)
            {
                resultList = jedis.blpop(timeout, key);// 获取并移除队列头部第一个元素
            }
            else
            {
                resultList = jedis.brpop(timeout, key);// 获取并移除队列尾部第一个元素
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource(jedis);
        }
        return resultList;
    }

    /**
     * @ClassName: Direction
     * @Description: 队列的位置头部或尾部
     * @author administrator
     * @date 2016年6月20日 上午10:54:22
     */
    public static enum Direction
    {
        Left, Right
    }

    /**
     * @Title: get
     * @Description:自增序列，Redis Incr 命令将 key 中储存的数字值增一。 如果 key 不存在，那么 key
     *                         的值会先被初始化为 0
     * 
     * @author administrator 2016年5月13日 上午9:27:49
     * @param key
     * @return
     * @throws
     */
    public static Long incr(String key)
    {
        Long value = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            value = jedis.incr(key);
            // jedis.incrBy(key, integer)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * @Title: get
     * @Description:自增序列，Redis Incr 命令将 key 中储存的数字值增一。 如果 key 不存在，那么 key
     *                         的值会先被初始化为 0
     * 
     * @author administrator 2016年5月13日 上午9:27:49
     * @param key
     * @return
     * @throws
     */
    public static Long incr(String key, Long seedValue)
    {
        Long value = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            value = jedis.incrBy(key, seedValue);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return value;
    }
    
    /**
    * @Title: set
    * @Description: 保存对象格式的数据
    * @author 赵天泽
    * @date  2017年3月8日 上午11:28:12
    * @param key
    * @param t
    * @param clazz
    * @return
    * @throws
     */
    public static <T> String set(String key,T t, Class<T> clazz)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.set(key, JSON.toJSONString(t));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }

    /**
    * @Title: set
    * @Description: 保存对象格式的数据并设置过期时间
    * @author 赵天泽
    * @date  2017年3月8日 上午11:27:29
    * @param key
    * @param t
    * @param clazz
    * @param expireSeconds	(过期时间，秒)
    * @return
    * @throws
     */
    public static <T> String set(String key,T t, Class<T> clazz,
            int expireSeconds)
    {
        String result = null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            result = jedis.set(key, JSON.toJSONString(t));
            jedis.expire(key, expireSeconds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return result;
    }
    
    /**
    * @Title: getobj
    * @Description: 获取对象格式数据
    * @author 赵天泽
    * @date  2017年3月8日 上午11:26:27
    * @param key
    * @param clazz
    * @return
    * @throws
     */
    public static <T> T get(String key,Class<T> clazz)
    {	
    	T t=null;
        Jedis jedis = null;
        try
        {
            pool = getConnection();
            jedis = pool.getResource();
            if (jedis.exists(key))
            {
                t = JSON.parseObject(jedis.get(key), clazz);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return t;
    }

    public static void main(String[] args)
    {
        System.out.println(incr("kaifeng"));
    }

}

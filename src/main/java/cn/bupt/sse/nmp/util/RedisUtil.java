package cn.bupt.sse.nmp.util;

import cn.bupt.sse.nmp.constants.ConstantsRedis;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-04 19:26
 **/
public class RedisUtil {
    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();

            //config.setMaxActive(ConstantsRedis.MAX_ACTIVE);
            config.setMaxIdle(ConstantsRedis.MAX_IDLE);
            config.setMaxWaitMillis(ConstantsRedis.MAX_WAIT);
            //config.setMaxWait(ConstantsRedis.MAX_WAIT);
            config.setTestOnBorrow(ConstantsRedis.TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ConstantsRedis.ADDR, ConstantsRedis.PORT,10000,ConstantsRedis.AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取生存时间
     * @param key
     * @return
     */
    public static long getTtl(String key)
    {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {

            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置字符串内容
     *
     * @param key
     *            key
     * @param value
     *            value
     * @return
     * @throws Exception
     */
    public static boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }


    /**
     * 设置过期时间
     *
     * @param key
     *            key
     * @param seconds
     *            seconds
     * @return
     * @throws Exception
     */
    public static boolean expire(String key,int seconds) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.expire(key, seconds) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, Object value) {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = RedisUtil.getJedis();
            jedis.set(key, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象byte[] byte[]
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            //String objectJson = JSON.toJSONString(value);
            jedis = RedisUtil.getJedis();
            jedis.select(1);
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 删除缓存中得对象，根据key
     *
     * @param key
     * @return
     */
    public static boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 删除缓存中得对象，根据key byte[]
     *
     * @param key
     * @return
     */
    public static boolean del(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.select(1);
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取内容
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            Object value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取内容byte[]
     *
     * @param key
     * @return
     */
    public static byte[] get(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.select(1);
            byte[] value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取对象
     *
     * @param key
     * @return
     */
    public static <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            String value = jedis.get(key);
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 删除所有key
     *
     * @return
     */
    public static String flushAll() {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 删除DB中的key
     *
     * @return
     */
    public static String flushDb() {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.select(1);
            return jedis.flushDB();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static boolean exist(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 获得db大小
     *
     * @param
     * @return
     */
    public static Long getSize() {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.select(1);
            Long value = jedis.dbSize();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * redis  对list的左侧push
     * @param key
     * @param strings
     * @return
     */
    public static boolean lpush(String key,String... strings){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            jedis.lpush(key,strings);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * redis  对list的左侧push
     * @param key
     * @param strings
     * @return
     */
    public static boolean rpush(String key,String... strings){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            jedis.rpush(key,strings);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

    public static String ltrim(String key,long left,long right) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.ltrim(key,left,right);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }




}

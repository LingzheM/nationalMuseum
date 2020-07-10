package cn.bupt.sse.nmp.util;

import cn.bupt.sse.nmp.constants.ConstantsRedis;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: nationalMuseum
 * @description:
 * @author: Ljx
 * @create: 2020-07-04 19:26
 **/
public class RedisUtil {
    //用来记录当前楼层的所有用户（包括游客 和 工作人员）的角色类型
    public static final String  ACTIVE_USER_TYPE = "type";
    //用来记录活跃用户的上一次访问的展品的id+初次到达展品的时间
    public static final String  ACTIVE_USER_EXHIBIT = "exhibit";
    //游客上次访问的楼层
    public static final String  ACTIVE_USER_FLOOR= "floor";
    public static final String  PERSON_NUMBER= "personNumber";
    //判定游客离开的时间间隔
    public static final Integer LEAVE_TIME = 5;
    //判断游客到达展品的距离
    public static final Integer USER_EXHIBITION_DISTANCE = 30;




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
     * redis  对list的右侧push
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

    /**
     * 用来清除list中的所有元素 left 1 right 0
     * @param key
     * @param left
     * @param right
     * @return
     */
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

    /**
     * 获取列表长度
     * @param key
     * @return
     */
    public static Long llen(String key){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.llen(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 移除并返回最左侧的元素
     * @param key
     * @return
     */
    public static String lpop(String key){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.lpop(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 移除并返回最右侧的元素
     * @param key
     * @return
     */
    public static String rpop(String key){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.rpop(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 获取列表中的全部元素
     * @param key
     * @return
     */
    public static List<String> lrange(String key, Long start, Long end){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.lrange(key,start,end);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 获取列表中的全部元素
     * @param key
     * @return
     */
    public static String lindex(String key,long index){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.lindex(key,index);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 想hash中添加整型元素
     * @param key
     * @return
     */
    public static Long hset(String key,String field,Integer type){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.hset(key,field,String.valueOf(type));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 想hash中添加时间值
     * @param key
     * @return
     */
    public static Long hset(String key,String field,Timestamp data){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.hset(key,field,String.valueOf(data.toString()));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 想hash中添加元素
     * @param key
     * @return
     */
    public static Long hset(String key,String field,String value){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.hset(key,field,value);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 判断hash中是否存在
     * @param key
     * @return
     */
    public static Boolean hexists(String key,String field){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.hexists(key,field);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 获取hash中的元素
     * @param key
     * @return
     */
    public static String hget(String key,String field){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.hget(key,field);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 删除hash中的值
     * @param key
     * @param field
     * @return
     */
    public static Long hdel(String key,String... field){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.hdel(key,field);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 获取hash的长度
     * @param key
     * @return
     */
    public static Long hlen(String key){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return jedis.hlen(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * 获取hash中的所有键
     * @param key
     * @return
     */
    public static List<String> hkeys(String key){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return new ArrayList<String>(jedis.hkeys(key));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     *批量获取hash中的key
     * @param key
     * @return
     */
    public static List<String> hmget(String key,String fields){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return new ArrayList<String>(jedis.hmget(key,fields));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }
    /**
     * hash中所有的值
     * @param key
     * @return
     */
    public static List<String> hvals(String key){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            return new ArrayList<String>(jedis.hkeys(key));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            RedisUtil.returnResource(jedis);
        }
    }

}

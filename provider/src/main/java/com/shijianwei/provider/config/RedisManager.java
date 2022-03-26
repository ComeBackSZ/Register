package com.shijianwei.provider.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
 
/**
 * @Author: feng
 * @Date: 2019/5/25 21:54
 * @Description:    Jedis配置(饿汉式单例)
 */
public class RedisManager {
 
    private static JedisPool jedisPool;

    @Autowired
    private static Environment env ;
 
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(18);
        jedisPoolConfig.setMaxIdle(5);
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.13.137",6379,5000);
    }
 
    public static Jedis getJedis() throws Exception {
        if (null != jedisPool){
            return jedisPool.getResource();
        }
        throw new Exception("Jedispool was not init");
    }
}
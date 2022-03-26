package com.shijianwei.provider.util;

import com.shijianwei.provider.config.RedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

public class RedisLock {

    public String getLock(String key, int timeout) {
        Jedis jedis = null;
        try {
            jedis = RedisManager.getJedis();
//            String value = UUID.randomUUID().toString();
            //以当前线程id作为value
            String value = String.valueOf(Thread.currentThread().getId());
            long end = System.currentTimeMillis() + timeout;
            while (System.currentTimeMillis() < end) {
                if (jedis.setnx(key, value) == 1) {
                    jedis.expire(key, timeout);
                    return value;
                }
                if (jedis.ttl(key) == -1) {
                    jedis.expire(key, timeout);
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    public boolean releaseLock(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = RedisManager.getJedis();
            while (true) {
                jedis.watch(key);
                System.out.println(jedis.get(key));
                if (value.equals(jedis.get(key))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(key);
                    List<Object> list = transaction.exec();
                    if (list == null) {
                        continue;
                    }
                }
                jedis.unwatch();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }
}
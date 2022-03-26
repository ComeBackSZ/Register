package com.shijianwei.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shijianwei.api.model.Goods;
import com.shijianwei.api.service.RobGoodsService;
import com.shijianwei.provider.config.RedissonConfig;
import com.shijianwei.provider.mapper.GoodsMapper;

import com.shijianwei.provider.util.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SHI
 * @date 2022/3/25 14:22
 */
@Service(
        version = "1.0.0",
        interfaceClass = RobGoodsService.class,
        interfaceName = "com.shijianwei.api.service.RobGoodsService"
)
@Slf4j
public class RobGoodsServiceImpl implements RobGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public String rob(Integer id) {
//        synchronized (this) {
        if ((goodsMapper.selectByPrimaryKey(id).getCount() <= 0)) {
            return null;
        } else {
            Goods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setCount(goods.getCount() - 1);
            goodsMapper.updateByPrimaryKey(goods);
            return goods.toString();
        }
//        }
    }


    @Override
    public synchronized String rob1(Integer id) {
//        synchronized (this) {
        if ((goodsMapper.selectByPrimaryKey(id).getCount() <= 0)) {
            return null;
        } else {
            Goods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setCount(goods.getCount() - 1);
            goodsMapper.updateByPrimaryKey(goods);
            return goods.toString();
        }
//        }
    }


//    private AtomicInteger redisi = new AtomicInteger();
//    private static final String REDISKEYS = "redis:robGoods";
//    @Override
//    public String rob2(Integer id) {
////        redis实现分布式锁
//        try {
//            RedisUtil redisUtil = new RedisUtil();
//            boolean res = redisUtil.set(REDISKEYS + redisi.incrementAndGet(), 1, TimeUtil.SECONDS_IN_DAY);
//            if (res) {
//                if (goodsMapper.selectByPrimaryKey(id).getCount() > 0){
//                    Goods goods = goodsMapper.selectByPrimaryKey(id);
//                    goods.setCount(goods.getCount() - 1);
//                    goodsMapper.updateByPrimaryKey(goods);
//                    System.out.println(goods.toString());
//                    return goods.toString();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//        }
//
//        return null;
//    }


    private static final String REDISSONKEYS = "redisson:robGoods";

    //    @Autowired
//    private Environment env ;
//    private RedissonClient redissonClient;
    @Override
    public String rob3(Integer id) {

        RLock lock = null;
//        RedissonUtils redissonUtils = null ;
//        RedissonClient redisson = null ;
        try {
            RedissonClient redissonConfig = RedissonConfig.getInstance();
            lock = redissonConfig.getLock(REDISSONKEYS);

            lock.lock();
//            redissonUtils = RedissonUtils.getInstance();
//            redisson = redissonUtils.getRedisson("192.168.13.137","6379");
//            lock = redisson.getLock(REDISSONKEYS);
            if (goodsMapper.selectByPrimaryKey(id).getCount() > 0) {
                Goods goods = goodsMapper.selectByPrimaryKey(id);
                goods.setCount(goods.getCount() - 1);
                goodsMapper.updateByPrimaryKey(goods);
                return goods.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (lock != null) {
//                lock.unlock();
//            }
//            if (redissonUtils != null && redisson != null) {
//                redissonUtils.closeRedisson(redisson);
//            }
            if (lock != null) {
                lock.unlock();
            }
        }
        return null;

    }


//  注：节点前面必须+ / 代表从根节点创建节点


    //定义Zookeeper客户端CuratorFramework实例
    @Autowired
    private CuratorFramework client;
    private static final String ZOOKEEPERNODES = "/ZK:robGoods";
    //Zookeeper分布式锁的实现原则是由ZNode节点的创建，删除与监听器构成的
    //而ZNode节点将对应一个具体的路径-根Unix文件路径类似-需要以/开头
    private AtomicInteger i = new AtomicInteger();

    @Override
    public String rob4(Integer id) {
        //创建Zookeeper互斥锁组件示例，需要将CuratorFramework实例，精心构造的共享资源作为构造参数
        InterProcessMutex mutex = new InterProcessMutex(client, ZOOKEEPERNODES );
        try {
            if (mutex.acquire(15, TimeUnit.SECONDS)) {
                if (goodsMapper.selectByPrimaryKey(id).getCount() > 0) {
                    Goods goods = goodsMapper.selectByPrimaryKey(id);
                    goods.setCount(goods.getCount() - 1);
                    goodsMapper.updateByPrimaryKey(goods);
                    return goods.toString();
                }
//                mutex.release();
            } else {
                System.out.println("null zookeeper lock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

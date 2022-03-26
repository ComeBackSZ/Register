package com.shijianwei.provider.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author SHI
 * @date 2022/3/26 13:23
 *
 * 不是Redisson的配置类，里面包含Redisson的各种模式下的创建对象
 */
public class RedissonConfig {

    private static volatile RedissonClient redisson;

    private RedissonConfig(){ }




    public static RedissonClient getInstance() {
        if (redisson == null) {
            synchronized (RedissonConfig.class) {
                if (redisson == null) {
//                    在下面选择了单机模式
                    Config config = new Config();
                    config.useSingleServer().setAddress("192.168.13.137:6379");
                    redisson = Redisson.create(config);
                }
            }
        }
        return redisson;
    }



//    public static void main(String[] args) {
        //单机
//        RedissonClient redisson = Redisson.create();
//        Config config = new Config();
//        config.useSingleServer().setAddress("myredisserver:6379");
//        RedissonClient redisson = Redisson.create(config);


//        //主从
//        Config config = new Config();
//        config.useMasterSlaveServers()
//                .setMasterAddress("127.0.0.1:6379")
//                .addSlaveAddress("127.0.0.1:6389", "127.0.0.1:6332", "127.0.0.1:6419")
//                .addSlaveAddress("127.0.0.1:6399");
//        RedissonClient redisson = Redisson.create(config);
//
//
////哨兵
//        Config config = new Config();
//        config.useSentinelServers()
//                .setMasterName("mymaster")
//                .addSentinelAddress("127.0.0.1:26389", "127.0.0.1:26379")
//                .addSentinelAddress("127.0.0.1:26319");
//        RedissonClient redisson = Redisson.create(config);
//
//
////集群
//        Config config = new Config();
//        config.useClusterServers()
//                .setScanInterval(2000) // cluster state scan interval in milliseconds
//                .addNodeAddress("127.0.0.1:7000", "127.0.0.1:7001")
//                .addNodeAddress("127.0.0.1:7002");
//        RedissonClient redisson = Redisson.create(config);
//    }
}

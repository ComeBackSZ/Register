package com.shijianwei.provider.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author SHI
 * @date 2022/3/25 16:31
 */
@Configuration

public class CuratorFrameworkConfig {
    @Autowired
    private Environment env;

    @Bean
    public CuratorFramework curatorFramework(){
        //创建CuratorFramework实例
        //（1）创建的方式是采用工厂模式进行创建；
        //（2）指定了客户端连接到ZooKeeper服务端的策略：这里是采用重试的机制(5次，每次间隔1s)
        CuratorFramework curatorFramework=
                CuratorFrameworkFactory.builder().connectString(env.getProperty("zk.host")).
                        //从application.yml文件中读取
                                namespace(env.getProperty("zk.namespace"))
                        .retryPolicy(new RetryNTimes(5,1000)).build();
        curatorFramework.start();
        //返回CuratorFramework实例
        return curatorFramework;
    }
}

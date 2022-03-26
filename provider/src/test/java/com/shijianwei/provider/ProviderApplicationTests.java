package com.shijianwei.provider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootTest
@Component
class ProviderApplicationTests {


    private static String host2;


    @Value("${spring.redis.host}")
    private String host;

    @Test
    void contextLoads() {
        System.out.println(host2);
        System.out.println(host);
    }


    @PostConstruct
    public void getServelPort(){
        host2 = this.host;
    }


}

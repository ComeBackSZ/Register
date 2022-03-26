package com.shijianwei.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shijianwei.api.service.RobGoodsService;
import com.shijianwei.api.service.UserLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author SHI
 * @date 2022/3/25 14:32
 */
@RestController
@RequestMapping("/goods")
@Api(tags = "RobGoodsController", description = "抢商品")
public class RobGoodsController {
    @Reference(
            version = "1.0.0",
            interfaceClass = RobGoodsService.class,
            interfaceName = "com.shijianwei.api.RobGoodsService",
            timeout =120000
    )
    public RobGoodsService robGoodsService;



    @Reference(
            version = "1.0.0",
            interfaceClass = UserLoginService.class,
            interfaceName = "com.shijianwei.api.UserLoginService",
            timeout =120000
    )
    public UserLoginService userLoginService;


    @ApiOperation(value = "不加锁抢商品")
    @GetMapping("/rob/{id}")
    public String login(@PathVariable Integer id) {
        return robGoodsService.rob(id);
    }



    @ApiOperation(value = "synchronized抢商品")
    @GetMapping("/robsyn/{id}")
    public String login1(@PathVariable Integer id) {
        return robGoodsService.rob1(id);
    }


//    @ApiOperation(value = "Redis抢商品")
//    @GetMapping("/robJedis/{id}")
//    public String login2(@PathVariable Integer id) {
//        return robGoodsService.rob2(id);
//    }

    @ApiOperation(value = "Redisson抢商品")
    @GetMapping("/robRedisson/{id}")
    public String login3(@PathVariable Integer id) {
        return robGoodsService.rob3(id);
    }

    @ApiOperation(value = "ZooKeeper抢商品")
    @GetMapping("/robZK/{id}")
    public String login4(@PathVariable Integer id) {
        return robGoodsService.rob4(id);
    }
}

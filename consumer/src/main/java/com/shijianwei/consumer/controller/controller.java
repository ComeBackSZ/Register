package com.shijianwei.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shijianwei.api.service.RobGoodsService;
import com.shijianwei.api.service.conService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SHI
 * @date 2022/3/25 21:21
 */
@RestController
@Api("conteoleler")
@RequestMapping("/con")
public class controller {
    @Reference(
            version = "1.0.0",
            interfaceClass = RobGoodsService.class,
            interfaceName = "com.shijianwei.api.RobGoodsService",
            timeout =120000
    )
    public RobGoodsService robGoodsService;

    @Reference(
            version = "1.0.0",
            interfaceClass = conService.class,
            interfaceName = "com.shijianwei.api.conService",
            timeout =120000
    )
    public conService conService;



    @ApiOperation(value = "Jedis rob goods !")
    @GetMapping("/rob/{id}")
    public String login(@PathVariable Integer id) {
        return conService.sa(id);
    }
}

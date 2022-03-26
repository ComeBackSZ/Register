package com.shijianwei.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shijianwei.api.model.User;
import com.shijianwei.api.service.RobGoodsService;
import com.shijianwei.api.service.UserLoginService;
import com.shijianwei.provider.service.UserLoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SHI
 * @date 2022/3/25 11:59
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {
    @Reference(
            version = "1.0.0",
            interfaceClass = UserLoginService.class,
            interfaceName = "com.shijianwei.api.UserLoginService",
            timeout =120000
    )
    public UserLoginService userLoginService;


    @Reference(
            version = "1.0.0",
            interfaceClass = RobGoodsService.class,
            interfaceName = "com.shijianwei.api.RobGoodsService",
            timeout =120000
    )
    public RobGoodsService robGoodsService;


    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userLoginService.userLogin(user);
    }


}

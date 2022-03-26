package com.shijianwei.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shijianwei.api.model.User;
import com.shijianwei.api.service.UserLoginService;
import com.shijianwei.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author SHI
 * @date 2022/3/25 12:01
 */

@Service(
        version ="1.0.0",
        interfaceClass = UserLoginService.class,
        interfaceName = "com.shijianwei.api.service.UserLoginService"
)
@Component
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public String userLogin(User user) {
        if (StringUtils.isEmpty(user)){
            return null ;
        }
        User getUser = userMapper.selectByPrimaryKey(user.getId());
        return getUser.toString();
    }
}

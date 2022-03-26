package com.shijianwei.api.service;

/**
 * @author SHI
 * @date 2022/3/25 14:21
 */
public interface RobGoodsService {
    String rob(Integer id);
    String rob1(Integer id);

//    redis分布式锁在conservice中
//    String rob2(Integer id);

    String rob3(Integer id);
    String rob4(Integer id);
}

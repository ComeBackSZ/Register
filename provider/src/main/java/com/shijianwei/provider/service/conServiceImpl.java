package com.shijianwei.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shijianwei.api.model.Goods;
import com.shijianwei.api.service.conService;
import com.shijianwei.provider.mapper.GoodsMapper;
import com.shijianwei.provider.util.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author SHI
 * @date 2022/3/25 21:30
 */
@Service(
        version = "1.0.0",
        interfaceClass = conService.class,
        interfaceName = "com.shijianwei.api.service.conService"
)
@Slf4j
public class conServiceImpl implements conService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 使用Jedis加锁来保证安全
     */
    private static final String JEDISKEY = "Jedis_key";
    @Override
    public String sa(Integer id) {
        log.info("conServiceImpl start");
        RedisLock redisLock = new RedisLock();
        String value = redisLock.getLock(JEDISKEY, 10);
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        if (goods.getCount() <= 0) {
            System.out.println("goods counts is less than zero!");
            redisLock.releaseLock(JEDISKEY, String.valueOf(Thread.currentThread().getId()));
            log.info("conServiceImpl stop !");
            return "goods counts is less than zero!";
        }else {
            goods.setCount(goods.getCount()-1);
            goodsMapper.updateByPrimaryKey(goods);
            System.out.println("goods update success!" + goods.toString());
            redisLock.releaseLock(JEDISKEY, String.valueOf(Thread.currentThread().getId()));
            log.info("conServiceImpl stop !");
            return "goods update success!" + goods.toString();
        }
    }
}

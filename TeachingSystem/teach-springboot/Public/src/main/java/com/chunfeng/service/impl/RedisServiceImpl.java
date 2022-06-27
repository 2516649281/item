package com.chunfeng.service.impl;

import com.alibaba.fastjson.JSON;
import com.chunfeng.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * redis业务层实现类
 *
 * @param <T> 实体
 */
@Service
public class RedisServiceImpl<T> implements IRedisService<T> {

    /**
     * redis操作对象
     */
    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 获取字符串值
     *
     * @param key 键
     * @return Object
     */
    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 存储字符串值
     *
     * @param key 键
     * @param t   值
     */
    @Override
    public void set(String key, T t) {
        String value = JSON.toJSONString(t);
        redisTemplate.opsForValue().set(key, value);
    }
}

package com.chunfeng.service;

/**
 * Redis服务层接口
 *
 * @param <T> 实体
 */
public interface IRedisService<T> {

    /**
     * 获取字符串值
     *
     * @param key 键
     * @return T
     */
    String get(String key);

    /**
     * 存储字符串值
     *
     * @param key 键
     * @param t   值
     */
    void set(String key, T t);
}

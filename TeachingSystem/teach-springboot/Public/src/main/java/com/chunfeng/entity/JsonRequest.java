package com.chunfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * JSON请求类
 *
 * @param <T> 任意类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonRequest<T> implements Serializable {
    private Integer statue = 200;//状态码
    private String message = "请求已完成!";//消息
    private T data;//数据
    private Long pageSize = 0L;//总数

    public JsonRequest(Integer statue, String message) {
        this.statue = statue;
        this.message = message;
    }

    public JsonRequest(String message, T data, Long pageSize) {
        this.message = message;
        this.data = data;
        this.pageSize = pageSize;
    }

    public JsonRequest(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public JsonRequest(T data, Long pageSize) {
        this.data = data;
        this.pageSize = pageSize;
    }

    public JsonRequest(T data) {
        this.data = data;
    }
}

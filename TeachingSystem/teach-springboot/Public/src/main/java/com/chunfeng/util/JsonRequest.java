package com.chunfeng.util;

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
    private Integer statue;//状态码
    private String message;//消息
    private T data;//数据
    private Long pageSize;//总数

    public JsonRequest(Integer statue, String message) {
        this.statue = statue;
        this.message = message;
    }

    public JsonRequest(Integer statue, String message, T data) {
        this.statue = statue;
        this.message = message;
        this.data = data;
    }
}

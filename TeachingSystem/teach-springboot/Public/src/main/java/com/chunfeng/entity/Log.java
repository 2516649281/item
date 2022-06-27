package com.chunfeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 日志表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;//日志编号
    private Integer deleted;//逻辑删除
    private String createTime;//创建时间
    private String updateTime;//修改时间

    public Log(String createTime) {
        this.createTime = createTime;
    }

    public Log(Long logId, String updateTime) {
        this.logId = logId;
        this.updateTime = updateTime;
    }

    public Log(Long logId, Integer deleted) {
        this.logId = logId;
        this.deleted = deleted;
    }

    public Log(Long logId, Integer deleted, String updateTime) {
        this.logId = logId;
        this.deleted = deleted;
        this.updateTime = updateTime;
    }
}

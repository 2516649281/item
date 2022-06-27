package com.chunfeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 管理员表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long adminId;//管理员编号
    private String adminName;//管理员姓名
    private Integer adminAge;//管理员年龄
    private Integer adminGender;//管理员性别
    private String adminAddress;//管理员住址
    private String adminPhone;//管理员电话
    private String adminEmail;//管理员邮箱
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;//日志编号
    @TableField(exist = false)
    private Log log;//日志信息
}

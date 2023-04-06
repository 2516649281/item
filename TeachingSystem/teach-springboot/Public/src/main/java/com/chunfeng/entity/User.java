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
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;//用户编号
    private String userName;//用户名
    @TableField(exist = false)
    private String newUserName;//新用户名
    private String userPassword;//用户密码
    @TableField(exist = false)
    private String newUserPassword;//新密码
    private String userSalt;//盐值
    private String userHeader;//头像
    @TableField(exist = false)
    private String newUserHeader;//新头像
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userIndex;//身份编号
    private Integer userIdentity;//身份指数
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;//日志编号
    @TableField(exist = false)
    private Log log;//日志信息
    //@TableField(exist = false)
    //private String token;//token字符串
    @TableField(exist = false)
    private Object user;//用户真实信息
}

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
 * 学生表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentId;//学生编号
    private String studentName;//学生姓名
    private Integer studentAge;//学生年龄
    private Integer studentGender;//学生性别
    private String studentAddress;//学生住址
    private String studentPhone;//学生电话
    private String studentEmail;//学生邮箱
    @JsonSerialize(using = ToStringSerializer.class)
    private Long classId;//班级编号
    @TableField(exist = false)
    private Class aClass;//班级信息
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;//日志编号
    @TableField(exist = false)
    private Log log;//日志信息
}

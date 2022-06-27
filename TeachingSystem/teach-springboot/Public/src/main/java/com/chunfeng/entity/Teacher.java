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
 * 教师表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long teacherId;//教师编号
    private String teacherName;//教师名
    private Integer teacherAge;//教师年龄
    private Integer teacherGender;//教师性别
    private String teacherAddress;//教师地址
    private String teacherPhone;//教师电话
    private String teacherEmail;//教师电话
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;//科目编号
    @TableField(exist = false)
    private Subject subject;//科目信息
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;//日志编号
    @TableField(exist = false)
    private Log log;//日志信息
}

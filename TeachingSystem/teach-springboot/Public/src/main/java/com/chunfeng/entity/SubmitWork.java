package com.chunfeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 提交作业表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitWork implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long submitId;//提交作业编号
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentId;//学生编号
    @TableField(exist = false)
    private Student student;//学生信息
    private Integer workId;//作业编号
    private String submitContent;//提交作业信息
    @TableField(exist = false)
    private MultipartFile submitFile;//作业实体
    private Integer submitScore;//成绩
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;//日志编号
    @TableField(exist = false)
    private Log log;//日志信息
}

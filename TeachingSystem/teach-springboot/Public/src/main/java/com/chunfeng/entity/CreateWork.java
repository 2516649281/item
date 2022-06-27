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
 * 教师端上传的作业信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWork implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long workId;//作业编号
    private String workName;//作业名
    private String workContent;//作业内容
    @JsonSerialize(using = ToStringSerializer.class)
    private Long teacherId;//教师编号
    @TableField(exist = false)
    private Teacher teacher;//教师信息
    @JsonSerialize(using = ToStringSerializer.class)
    private Long classId;//班级编号
    @TableField(exist = false)
    private Class aClass;//班级信息
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;//日志信息
    @TableField(exist = false)
    private Log log;//日志

    public CreateWork(String workName, String workContent, Long teacherId, Long classId) {
        this.workName = workName;
        this.workContent = workContent;
        this.teacherId = teacherId;
        this.classId = classId;
    }

    public CreateWork(Long workId, String workName, String workContent, Long teacherId, Long classId) {
        this.workId = workId;
        this.workName = workName;
        this.workContent = workContent;
        this.teacherId = teacherId;
        this.classId = classId;
    }
}

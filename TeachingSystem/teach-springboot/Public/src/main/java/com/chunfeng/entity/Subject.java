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
 * 科目表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;//科目编号
    private String subjectName;//科目名
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;//日志编号
    @TableField(exist = false)
    private Log log;
}

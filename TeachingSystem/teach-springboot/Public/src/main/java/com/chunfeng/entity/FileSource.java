package com.chunfeng.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("file")
public class FileSource implements Serializable {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fileId;//文件编号
    private String fileName;//文件名称
    private String filePath;//文件路径
    private String fileType;//文件类型
    private String fileSize;//文件大小
    private String fileUploadTime;//文件上传时间
    private String fileUpdateTime;//文件修改时间

    public FileSource(String fileName, String filePath, String fileType, String fileSize, String fileUploadTime, String fileUpdateTime) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileUploadTime = fileUploadTime;
        this.fileUpdateTime = fileUpdateTime;
    }


    public FileSource(Long fileId, String fileName, String filePath, String fileType, String fileSize, String fileUploadTime) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileUploadTime = fileUploadTime;
    }
}

package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.FileSource;
import com.chunfeng.util.JsonRequest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件业务层
 */
public interface IFileService extends IService<FileSource> {

    /**
     * 一次上传一个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @return 上传是否成功
     */
    JsonRequest<Map<Long, Integer>> loadFile(byte[] bytes, Integer fileSize, String fileName);

    /**
     * 一次上传多个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @return 上传是否成功
     */
    JsonRequest<Map<Long, Integer>> loadFiles(List<byte[]> bytes, Integer[] fileSize, String[] fileName);

    /**
     * 修改一个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @param fileId   文件编号
     * @return 修改是否成功
     */
    JsonRequest<Integer> updateFile(byte[] bytes, Integer fileSize, String fileName, Long fileId);


    /**
     * 一次修改多个文件
     *
     * @param bytes       文件字节数组
     * @param fileSize    文件大小
     * @param oldFileName 文件名
     * @param fileId      文件编号
     * @return 修改是否成功
     */
    JsonRequest<Integer> updateFiles(List<byte[]> bytes, Integer[] fileSize, String[] oldFileName, Long[] fileId);

    /**
     * 删除一个文件
     *
     * @param fileId 数据库编号
     * @return 删除是否成功
     */
    JsonRequest<Integer> deleteFile(Long fileId);

    /**
     * 一次删除多个文件
     *
     * @param fileIds 数据库中文件编号
     * @return 删除是否成功
     */
    JsonRequest<Integer> deleteFiles(Long[] fileIds);

    /**
     * 读取一个文件
     *
     * @param fileId 文件编号
     * @return 文件实体
     */
    ResponseEntity<FileSystemResource> selectFile(Long fileId);

    /**
     * 读取多个文件
     *
     * @param fileIds 文件编号
     * @return 文件集合
     */
    List<ResponseEntity<FileSystemResource>> selectFiles(Long[] fileIds);
}

package com.chunfeng.controller;

import com.chunfeng.entity.JsonRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * feign核心接口
 */
@FeignClient("FileServer/file")
public interface FileClientController {

    /**
     * 一次上传一个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @return 上传是否成功
     */
    @PostMapping("/load")
    JsonRequest<Map<Long, Integer>> loadFile(@RequestBody byte[] bytes, @RequestParam("fileSize") Integer fileSize, @RequestParam("fileName") String fileName);

    /**
     * 修改一个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @param fileId   原始文件的数据库编号
     * @return 修改是否成功
     */
    @PutMapping("/update/{fileId}")
    JsonRequest<Map<Long, Integer>> loadFiles(@RequestBody List<byte[]> bytes, @RequestParam("fileSize") Integer[] fileSize, @RequestParam("fileName") String[] fileName, @PathVariable("fileId") Long fileId);


    /**
     * 删除一个文件
     *
     * @param fileId 数据库编号
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{fileId}")
    JsonRequest<Integer> deleteFile(@PathVariable("fileId") Long fileId);

    /**
     * 读取一个文件
     *
     * @param fileId 文件编号
     * @return 文件实体
     */
    @GetMapping("/select/{fileId}")
    ResponseEntity<FileSystemResource> selectFile(@PathVariable("fileId") Long fileId);
}

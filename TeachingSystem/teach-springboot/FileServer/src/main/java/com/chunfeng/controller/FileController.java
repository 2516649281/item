package com.chunfeng.controller;

import com.chunfeng.entity.JsonRequest;
import com.chunfeng.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文件操作controller
 */
@RestController
@RequestMapping("/file")
public class FileController extends ServiceController {

    /**
     * 文件业务层
     */
    @Autowired(required = false)
    private IFileService fileService;

    /**
     * 一次上传一个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @return 上传是否成功
     */
    @PostMapping("/load")
    public JsonRequest<Map<Long, Integer>> loadFile(@RequestBody byte[] bytes, @RequestParam("fileSize") Integer fileSize, @RequestParam("fileName") String fileName) {
        return fileService.loadFile(bytes, fileSize, fileName);
    }


    /**
     * 一次上传多个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @return 上传是否成功
     */
    @PostMapping("/loads")
    public JsonRequest<Map<Long, Integer>> loadFiles(@RequestBody List<byte[]> bytes, @RequestParam("fileSize") Integer[] fileSize, @RequestParam("fileName") String[] fileName) {
        return fileService.loadFiles(bytes, fileSize, fileName);
    }

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
    public JsonRequest<Integer> updateFile(@RequestBody byte[] bytes, @RequestParam("fileSize") Integer fileSize, @RequestParam("fileName") String fileName, @PathVariable Long fileId) {
        return fileService.updateFile(bytes, fileSize, fileName, fileId);
    }

    /**
     * 一次修改多个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @param fileId   文件原始编号
     * @return 修改是否成功
     */
    @PutMapping("/updates")
    public JsonRequest<Integer> updateFiles(@RequestBody List<byte[]> bytes, @RequestParam("fileSize") Integer[] fileSize, @RequestParam("fileName") String[] fileName, @RequestParam Long[] fileId) {
        return fileService.updateFiles(bytes, fileSize, fileName, fileId);
    }

    /**
     * 删除一个文件
     *
     * @param fileId 数据库编号
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{fileId}")
    public JsonRequest<Integer> deleteFile(@PathVariable Long fileId) {
        return fileService.deleteFile(fileId);
    }

    /**
     * 一次删除多个文件
     *
     * @param fileIds 数据库中文件编号
     * @return 删除是否成功
     */
    @DeleteMapping("/deletes")
    public JsonRequest<Integer> deleteFiles(@RequestBody Long[] fileIds) {
        return fileService.deleteFiles(fileIds);
    }

    /**
     * 读取一个文件
     *
     * @param fileId 文件编号
     * @return 文件实体
     */
    @GetMapping("/select/{fileId}")
    public ResponseEntity<FileSystemResource> selectFile(@PathVariable Long fileId) {
        return fileService.selectFile(fileId);
    }

    /**
     * 读取多个文件
     *
     * @param fileIds 文件编号
     * @return 文件集合
     */
    @GetMapping("/selects")
    public List<ResponseEntity<FileSystemResource>> selectFiles(@RequestBody Long[] fileIds) {
        return fileService.selectFiles(fileIds);
    }
}

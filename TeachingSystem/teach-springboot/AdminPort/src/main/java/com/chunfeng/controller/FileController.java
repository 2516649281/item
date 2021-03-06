package com.chunfeng.controller;

import com.chunfeng.entity.FileSource;
import com.chunfeng.service.impl.FileClientController;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件管理控制层
 */
@RestController
@RequestMapping("/file")
public class FileController extends ServiceController {

    /**
     * 文件feign客户端
     */
    @Autowired
    private FileClientController fileClientController;


    /**
     * 查询所有已知文件
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @GetMapping("/select/{current}/{size}")
    JsonRequest<List<FileSource>> selectAllFile(@PathVariable Integer current, @PathVariable Integer size) {
        return fileClientController.selectAllFile(current, size);
    }

    /**
     * 条件查询
     *
     * @param current    页码
     * @param size       页长
     * @param fileSource 查询的条件
     * @return JSON
     */
    @GetMapping("/selectB/{current}/{size}")
    JsonRequest<List<FileSource>> selectAllFileBySource(@PathVariable Integer current, @PathVariable Integer size, String fileName) {
        return fileClientController.selectAllFileBySource(current, size, fileName);
    }
}

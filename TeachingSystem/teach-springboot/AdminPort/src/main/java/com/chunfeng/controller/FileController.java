package com.chunfeng.controller;

import com.chunfeng.entity.FileSource;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.service.impl.FileClientController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired(required = false)
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
     * @param current  页码
     * @param size     页长
     * @param fileName 查询的条件
     * @return JSON
     */
    @GetMapping("/selectB/{current}/{size}")
    JsonRequest<List<FileSource>> selectAllFileBySource(@PathVariable Integer current, @PathVariable Integer size, @RequestParam("fileName") String fileName) {
        return fileClientController.selectAllFileBySource(current, size, fileName);
    }
}

package com.chunfeng.service.impl;

import com.chunfeng.entity.FileSource;
import com.chunfeng.entity.JsonRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 文件管理feign客户端
 */
@FeignClient("FileServer/fileMage")
public interface FileClientController {

    /**
     * 查询所有已知文件
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @GetMapping("/select/{current}/{size}")
    JsonRequest<List<FileSource>> selectAllFile(@PathVariable Integer current, @PathVariable Integer size);


    /**
     * 条件查询
     *
     * @param current  页码
     * @param size     页长
     * @param fileName 查询的条件
     * @return JSON
     */
    @GetMapping("/selectB/{current}/{size}")
    JsonRequest<List<FileSource>> selectAllFileBySource(@PathVariable Integer current, @PathVariable Integer size, @RequestParam("fileName") String fileName);
}

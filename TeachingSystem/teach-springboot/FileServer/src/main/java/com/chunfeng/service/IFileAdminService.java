package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.FileSource;
import com.chunfeng.util.JsonRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 文件管理业务层接口
 */
public interface IFileAdminService extends IService<FileSource> {

    /**
     * 查询所有已知文件
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    JsonRequest<List<FileSource>> selectAllFile(Integer current, Integer size);

    /**
     * 条件查询
     *
     * @param current  页码
     * @param size     页长
     * @param fileName 查询的条件
     * @return JSON
     */
    JsonRequest<List<FileSource>> selectFileBySource(Integer current, Integer size, @RequestParam String fileName);
}

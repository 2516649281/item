package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.FileMapper;
import com.chunfeng.entity.FileSource;
import com.chunfeng.service.IFileAdminService;
import com.chunfeng.service.ex.fileException.look.FileLookIsNullException;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件管理业务层层
 */
@Service
public class FileAdminImpl extends ServiceImpl<FileMapper, FileSource> implements IFileAdminService {

    /**
     * 文件持久层
     */
    @Autowired
    private FileMapper fileMapper;

    /**
     * 查询所有已知文件
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @Cacheable(value = "file_page", key = "#current")
    @Override
    public JsonRequest<List<FileSource>> selectAllFile(Integer current, Integer size) {
        Page<FileSource> fileSourcePage = fileMapper.selectPage(
                new Page<>(current, size),
                new LambdaQueryWrapper<FileSource>()
                        .orderByAsc(FileSource::getFileUpdateTime));
        long pageSize = fileSourcePage.getTotal();
        if (pageSize < 1) {
            throw new FileLookIsNullException("文件库无数据!");
        }
        return new JsonRequest<>(200, null, fileSourcePage.getRecords(), pageSize);
    }

    /**
     * 条件查询
     *
     * @param current  页码
     * @param size     页长
     * @param fileName 查询的条件
     * @return JSON
     */
    @Cacheable(value = "file_page_source", key = "#fileName+'_'+#current")
    @Override
    public JsonRequest<List<FileSource>> selectFileBySource(Integer current, Integer size, String fileName) {
        Page<FileSource> sourcePage = fileMapper.selectPage(
                new Page<>(current, size),//分页查询
                new LambdaQueryWrapper<FileSource>()
                        .like(FileSource::getFileName, fileName)//模糊查询
                        .orderByAsc(FileSource::getFileUpdateTime));//根据修改时间排序
        long pageSize = sourcePage.getTotal();
        if (pageSize < 1) {
            throw new FileLookIsNullException("文件库无数据!");
        }
        return new JsonRequest<>(200, null, sourcePage.getRecords(), pageSize);
    }
}

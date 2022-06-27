package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.FileMapper;
import com.chunfeng.entity.FileSource;
import com.chunfeng.service.IFileService;
import com.chunfeng.service.ex.fileException.delete.FileDeleteErrorException;
import com.chunfeng.service.ex.fileException.delete.FileDeleteIsNullException;
import com.chunfeng.service.ex.fileException.load.FileLoadErrorException;
import com.chunfeng.service.ex.fileException.load.FileLoadIsExistException;
import com.chunfeng.service.ex.fileException.load.FileLoadIsNullException;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件业务层
 */
@Service
@Transactional
public class FileServiceImpl extends ServiceImpl<FileMapper, FileSource> implements IFileService {

    /**
     * 服务器文件存放路径
     */
    @Value("${file.Path}")
    private String filePath;


    /**
     * 时间格式
     */
    @Value("${date.format}")
    private String dateFormat;

    /**
     * 文件持久层
     */
    @Autowired
    private FileMapper fileMapper;


    /**
     * 一次上传一个文件
     *
     * @param fileByte    文件字节数组
     * @param fileSize    文件大小
     * @param oldFileName 文件名
     * @return 上传是否成功
     */
    @CacheEvict(value = {"file_page", "file_page_source"}, allEntries = true)
    @Override
    public JsonRequest<Map<Long, Integer>> loadFile(byte[] fileByte, Integer fileSize, String oldFileName) {
        Map<Long, Integer> map = new HashMap<>();
        String[] types = oldFileName.split("\\.");
        String fileType = types[types.length - 1];//截取文件最后一位以.分割的字符串,必定是后缀名
        String fileName = getFileName(fileType);//生成文件名
        List<FileSource> loadFileSources = fileMapper.selectList(
                new LambdaQueryWrapper<FileSource>().
                        eq(FileSource::getFileName, fileName));//按文件名条件查询
        //判断文件是否存在
        if (loadFileSources.size() > 0) {
            throw new FileLoadIsExistException("文件已存在");
        }
        try {
            //上传文件
            runLoadFile(fileByte, fileSize, fileName);
        } catch (Exception e) {
            throw new FileLoadErrorException("文件上传失败");
        }
        FileSource source = new FileSource(fileName,
                filePath + fileName,
                fileType, fileSize + "K",
                new SimpleDateFormat(dateFormat).format(new Date()),
                new SimpleDateFormat(dateFormat).format(new Date()));
        //将数据保存到数据库中
        int column = fileMapper.insert(source);
        if (column < 1) {
            throw new FileLoadErrorException("文件上传失败");
        }
        map.put(source.getFileId(), column);
        return new JsonRequest<>(200, "文件上传成功", map);
    }

    /**
     * 一次上传多个文件
     *
     * @param bytes    文件字节数组
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @return 上传是否成功
     */
    @CacheEvict(value = {"file_page", "file_page_source"}, allEntries = true)
    @Override
    public JsonRequest<Map<Long, Integer>> loadFiles(List<byte[]> bytes, Integer[] fileSize, String[] fileName) {
        Map<Long, Integer> loadFile = new HashMap<>();
        int column = 0;
        for (int i = 0; i < bytes.size(); i++) {
            //多次调用
            JsonRequest<Map<Long, Integer>> request = loadFile(bytes.get(i), fileSize[i], fileName[i]);
            if (request.getData() != null) {
                //合并集合元素
                loadFile.putAll(request.getData());
            }
        }
        //计算影响行数
        for (Integer value : loadFile.values()) {
            column += value;
        }
        return new JsonRequest<>(200, column + "个文件上传成功", loadFile);
    }

    /**
     * 修改一个文件
     *
     * @param bytes       文件字节数组
     * @param fileSize    文件大小
     * @param oldFileName 文件名
     * @param fileId      原始文件的数据库编号
     * @return 修改是否成功
     */
    @CacheEvict(value = {"file_page", "file_page_source"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateFile(byte[] bytes, Integer fileSize, String oldFileName, Long fileId) {
        String[] types = oldFileName.split("\\.");
        String fileType = types[types.length - 1];//截取文件最后一位以.分割的字符串,必定是后缀名
        String fileName = getFileName(fileType);//生成文件名
        try {
            //上传新文件
            runLoadFile(bytes, fileSize, fileName);
        } catch (Exception e) {
            throw new FileLoadErrorException("文件上传失败");
        }
        //获取原文件
        File file = new File(fileMapper.selectById(fileId).getFilePath());
        if (file.exists()) {
            //删除源文件
            if (!file.delete()) {
                throw new FileDeleteErrorException("重置文件失败!");
            }
        }
        int column = fileMapper.updateById(
                new FileSource(fileId,
                        fileName, //文件名
                        filePath + fileName, //文件路径
                        fileType, //文件类型
                        fileSize + "K", //文件大小
                        new SimpleDateFormat(dateFormat).format(new Date())));
        if (column < 1) {
            throw new FileDeleteErrorException("修改文件失败!");
        }
        return new JsonRequest<>(200, "文件修改成功!", column);
    }

    /**
     * 一次修改多个文件
     *
     * @param bytes       文件字节数组
     * @param fileSize    文件大小
     * @param oldFileName 文件名
     * @param fileId      文件原始编号
     * @return 修改是否成功
     */
    @CacheEvict(value = {"file_page", "file_page_source"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateFiles(List<byte[]> bytes, Integer[] fileSize, String[] oldFileName, Long[] fileId) {
        Integer column = 0;
        for (int i = 0; i < bytes.size(); i++) {
            //循环调用
            JsonRequest<Integer> jsonRequest = updateFile(bytes.get(i), fileSize[i], oldFileName[i], fileId[i]);
            column += jsonRequest.getData();
        }
        return new JsonRequest<>(200, column + "个文件修改完成", column);
    }

    /**
     * 删除一个文件
     *
     * @param fileId 数据库编号
     * @return 删除是否成功
     */
    @CacheEvict(value = {"file_page", "file_page_source"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteFile(Long fileId) {
        //获取文件
        FileSource fileSource = fileMapper.selectById(fileId);
        if (fileSource == null) {
            throw new FileDeleteIsNullException("文件不存在!");
        }
        File file = new File(fileSource.getFilePath());
        if (file.exists()) {
            //删除文件本体
            if (!file.delete()) {
                throw new FileDeleteErrorException("文件删除失败!");
            }
        }
        //删除文件所在数据库
        int column = fileMapper.deleteById(fileId);
        if (column < 1) {
            throw new FileDeleteErrorException("文件删除失败!");
        }
        return new JsonRequest<>(200, "删除成功!", column);
    }

    /**
     * 一次删除多个文件
     *
     * @param fileIds 数据库中文件编号
     * @return 删除是否成功
     */
    @CacheEvict(value = {"file_page", "file_page_source"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteFiles(Long[] fileIds) {
        int column = 0;
        for (Long fileId : fileIds) {
            //循环调用
            JsonRequest<Integer> jsonRequest = deleteFile(fileId);
            column += jsonRequest.getData();
        }
        return new JsonRequest<>(200, column + "个文件已删除!", column);
    }

    /**
     * 读取一个文件
     *
     * @param fileId 文件编号
     * @return 文件实体
     */
    @Override
    public ResponseEntity<FileSystemResource> selectFile(Long fileId) {
        //获取文件路径
        FileSource fileSource = fileMapper.selectById(fileId);
        if (fileSource == null) {
            throw new FileDeleteErrorException("读取失败!");
        }
        File file = new File(fileSource.getFilePath());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }

    /**
     * 读取多个文件
     *
     * @param fileIds 文件编号
     * @return 文件集合
     */
    @Override
    public List<ResponseEntity<FileSystemResource>> selectFiles(Long[] fileIds) {
        List<ResponseEntity<FileSystemResource>> files = new ArrayList<>();
        for (Long fileId : fileIds) {
            ResponseEntity<FileSystemResource> entity = selectFile(fileId);
            files.add(entity);
        }
        return files;
    }


    /**
     * 随机生成文件名称
     *
     * @param fileType 文件类型
     * @return 名称
     */
    public String getFileName(String fileType) {
        if (fileType == null || fileType.equals("")) {
            throw new FileLoadIsNullException("文件不得为空!");
        }
        return UUID.randomUUID().toString().toUpperCase() + "." + fileType;
    }

    /**
     * 将字节流转换成文件再上传到指定目录
     *
     * @param multipartFile 文件字节流
     * @param fileSize      文件大小
     * @param fileName      文件名称
     */
    public void runLoadFile(byte[] multipartFile, Integer fileSize, String fileName) {
        ByteArrayInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            //将字节数组转换成字节流
            inputStream = new ByteArrayInputStream(multipartFile);
            //创建输出流，并读取
            outputStream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            byte[] b = new byte[fileSize];
            //写文件
            while (inputStream.read(b) != -1) {
                outputStream.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //释放资源
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

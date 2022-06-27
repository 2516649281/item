package com.chunfeng.controller;

import com.chunfeng.util.JsonRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 */
@Controller
@Slf4j
public class ServiceController {

    /**
     * 状态码
     */
    private Integer statue = 200;

    /**
     * 全局捕获异常方法
     *
     * @param e 运行时异常
     * @return JSON
     */
    @ExceptionHandler
    JsonRequest<Void> getException(Exception e) {
        //获取类名并判断
        switch (e.getClass().getSimpleName()) {
            ////////////////////添加异常/////////////////////////
            case "AddSourceIsExistException"://添加时数据已存在异常
                statue = 4001;
                break;
            case "LogAddErrorException"://添加时日志输出异常
                statue = 4002;
                break;
            case "DeleteSourceIsNullException"://删除时该数据不存在
                statue = 5001;
                break;
            case "LogDeleteErrorException"://删除时日志输出异常
                statue = 5002;
                break;
            ////////////////////查询异常/////////////////////////
            case "SelectSourceIsNullException"://查询时数据为空
                statue = 6001;
                break;
            case "SelectSourceIsDeletedException"://查询时数据存在但已被删除
                statue = 6002;
                break;
            case "LogSelectErrorException"://查询时日志输出异常
                statue = 6003;
                break;
            ////////////////////修改异常/////////////////////////
            case "UpdateSourceIsNullException"://修改时数据不存在
                statue = 7001;
                break;
            case "LogUpdateErrorException"://修改时日志输出异常
                statue = 7002;
                break;
            ////////////////////token异常/////////////////////////
            case "TokenIsNullException"://token为空
                statue = 8001;
                break;
            case "TokenVerifyErrorException"://token验证失败
                statue = 8002;
                break;
            //////////////////////文件异常//////////////////////
            case "FileDeleteErrorException"://删除失败
                statue = 9001;
                break;
            case "FileDeleteIsNullException"://删除的文件为空
                statue = 9002;
                break;
            case "FileLoadErrorException"://上传失败
                statue = 9003;
                break;
            case "FileLoadIsExistException"://上传的文件已存在
                statue = 10002;
                break;
            case "FileLoadIsnullException"://上传文件请求的参数为空
                statue = 10003;
                break;
            case "FileLookErrorException"://文件查看失败
                statue = 11001;
                break;
            case "FileLookIsNullException"://查看的文件为空
                statue = 11002;
                break;
            case "FileUpdateErrorException"://修改文件失败
                statue = 12001;
                break;
            case "FileUpdateIsNullException"://修改的文件为空
                statue = 12002;
                break;
            ///////////////////缓存异常/////////////////////
            case "RedisClearErrorException":
                statue = 13001;
                break;
            ///////////////////未知或未定义的异常/////////////////////
            case "AddException"://添加数据的未知异常
                statue = 4000;
                break;
            case "DeleteException"://删除数据的未知异常
                statue = 5000;
                break;
            case "SelectException"://查询数据的未知异常
                statue = 6000;
                break;
            case "UpdateException"://修改数据的未知异常
                statue = 7000;
                break;
            case "TokenException"://token的未知异常
                statue = 800;
                break;
            case "FileException"://文件删除的未知异常
                statue = 9000;
                break;
            case "RedisException"://缓存的未知异常
                statue = 13000;
                break;
            default:
                statue = 500;//其他未知异常
        }
        log.warn("全局异常捕获器捕获到异常:" + e);
        return new JsonRequest<>(statue, e.getMessage());
    }
}

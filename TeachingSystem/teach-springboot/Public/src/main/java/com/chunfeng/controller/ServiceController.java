package com.chunfeng.controller;

import com.chunfeng.entity.JsonRequest;
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
    public static Integer STATUS = 200;

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
                STATUS = 4001;
                break;
            case "AddSourceErrorException"://添加时数据已存在异常
                STATUS = 4002;
                break;
            ////////////////////删除异常/////////////////////////
            case "DeleteSourceIsNullException"://删除时该数据不存在
                STATUS = 5001;
                break;
            case "DeleteSourceErrorException"://删除时该数据不存在
                STATUS = 5002;
                break;
            ////////////////////查询异常/////////////////////////
            case "SelectSourceIsNullException"://查询时数据为空
                STATUS = 6001;
                break;
            case "SelectSourceIsDeletedException"://查询时数据存在但已被删除
                STATUS = 6002;
                break;
            ////////////////////修改异常/////////////////////////
            case "UpdateSourceIsNullException"://修改时数据不存在
                STATUS = 7001;
                break;
            ////////////////////token异常/////////////////////////
            case "TokenIsNullException"://token为空
                STATUS = 8001;
                break;
            case "TokenVerifyErrorException"://token验证失败
                STATUS = 8002;
                break;
            //////////////////////文件异常//////////////////////
            case "FileDeleteErrorException"://删除失败
                STATUS = 9001;
                break;
            case "FileDeleteIsNullException"://删除的文件为空
                STATUS = 9002;
                break;
            case "FileLoadErrorException"://上传失败
                STATUS = 9003;
                break;
            case "FileLoadIsExistException"://上传的文件已存在
                STATUS = 10002;
                break;
            case "FileLoadIsnullException"://上传文件请求的参数为空
                STATUS = 10003;
                break;
            ///////////////////下载文件异常/////////////////////
            case "FileLookErrorException"://文件查看失败
                STATUS = 11001;
                break;
            case "FileLookIsNullException"://查看的文件为空
                STATUS = 11002;
                break;
            case "FileUpdateErrorException"://修改文件失败
                STATUS = 12001;
                break;
            case "FileUpdateIsNullException"://修改的文件为空
                STATUS = 12002;
                break;
            ///////////////////缓存异常/////////////////////
            case "RedisClearErrorException":
                STATUS = 13001;
                break;
            //////////////////////日志异常//////////////////////
            case "LogAddErrorException"://添加日志输出异常
                STATUS = 14001;
                break;
            case "LogAddSourceIsExistException"://添加日志时数据已存在
                STATUS = 14002;
                break;
            case "LogDeleteErrorException"://删除日志输出异常
                STATUS = 15001;
                break;
            case "LogDeleteIsNullException"://删除日志时为空
                STATUS = 15002;
                break;
            case "LogSelectErrorException"://查询时日志输出异常
                STATUS = 16001;
                break;
            case "LogSelectIsNullException"://查询日志时为空
                STATUS = 16002;
                break;
            case "LogUpdateErrorException"://修改时日志输出异常
                STATUS = 17001;
                break;
            case "LogUpdateSourceIsNullException"://修改日志时数据为空
                STATUS = 17002;
                break;
            ///////////////////未知或未定义的异常/////////////////////
            case "AddException"://添加数据的未知异常
                STATUS = 4000;
                break;
            case "DeleteException"://删除数据的未知异常
                STATUS = 5000;
                break;
            case "SelectException"://查询数据的未知异常
                STATUS = 6000;
                break;
            case "UpdateException"://修改数据的未知异常
                STATUS = 7000;
                break;
            case "TokenException"://token的未知异常
                STATUS = 800;
                break;
            case "FileException"://文件的未知异常
                STATUS = 9000;
                break;
            case "RedisException"://缓存的未知异常
                STATUS = 13000;
                break;
            case "LogException"://日志异常
                STATUS = 14000;
                break;
            case "SourceException"://数据类异常
                STATUS = 15000;
                break;
            default:
                STATUS = 500;//其他未知异常
        }
        log.warn("全局异常捕获器捕获到异常:" + e);
        return new JsonRequest<>(STATUS, e.getMessage());
    }
}

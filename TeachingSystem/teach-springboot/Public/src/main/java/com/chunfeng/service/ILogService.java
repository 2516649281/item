package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.Log;

/**
 * 日志业务层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public interface ILogService extends IService<Log> {

    /**
     * 根据日志编号查询日志信息
     *
     * @param logId 日志编号
     * @return JSON
     */
    JsonRequest<Log> selectLogById(Long logId);

    /**
     * 添加日志信息
     *
     * @param log 日志信息
     * @return JSON
     */
    JsonRequest<Integer> insertLog(Log log);

    /**
     * 修改日志信息
     *
     * @param log 日志信息
     * @return JSON
     */
    JsonRequest<Integer> updateLogById(Log log);
}

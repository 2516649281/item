package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.LogMapper;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.Log;
import com.chunfeng.service.ILogService;
import com.chunfeng.service.ex.logException.addException.LogAddErrorException;
import com.chunfeng.service.ex.logException.selectException.LogSelectIsNullException;
import com.chunfeng.service.ex.logException.updateException.LogUpdateErrorException;
import com.chunfeng.service.ex.logException.updateException.LogUpdateSourceIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日志业务层
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
@Service
@Transactional
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    /**
     * 日志持久层
     */
    @Autowired(required = false)
    private LogMapper logMapper;

    /**
     * 根据日志编号查询日志信息
     *
     * @param logId 日志编号
     * @return JSON
     */
    @Cacheable(value = "log_id", key = "#logId")
    @Override
    public JsonRequest<Log> selectLogById(Long logId) {
        Log log = logMapper.selectById(logId);
        if (log == null) {
            throw new LogSelectIsNullException("日志拉取失败!");
        }
        return new JsonRequest<>("查询成功!", log);
    }

    /**
     * 添加日志信息
     *
     * @param log 日志信息
     * @return JSON
     */
    @CacheEvict(value = "log_id", allEntries = true)
    @Override
    public JsonRequest<Integer> insertLog(Log log) {
        int column = logMapper.insert(log);
        if (column < 1) {
            throw new LogAddErrorException("添加日志失败!");
        }
        return new JsonRequest<>("添加日志成功!", column);
    }

    /**
     * 修改日志信息
     *
     * @param log 日志信息
     * @return JSON
     */
    @CacheEvict(value = "log_id", allEntries = true)
    @Override
    public JsonRequest<Integer> updateLogById(Log log) {
        Log data = selectLogById(log.getLogId()).getData();
        if (data == null) {
            throw new LogUpdateSourceIsNullException("日志信息不存在!");
        }
        int column = logMapper.updateById(log);
        if (column < 1) {
            throw new LogUpdateErrorException("");
        }
        return new JsonRequest<>("修改日志成功!", column);
    }
}

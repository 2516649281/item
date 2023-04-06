package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.SubmitWorkMapper;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.Log;
import com.chunfeng.entity.SubmitWork;
import com.chunfeng.service.ILogService;
import com.chunfeng.service.IWorkService;
import com.chunfeng.service.ex.logException.updateException.LogUpdateErrorException;
import com.chunfeng.service.ex.sourceException.addException.AddException;
import com.chunfeng.service.ex.sourceException.deleteExcpption.DeleteException;
import com.chunfeng.service.ex.sourceException.deleteExcpption.DeleteSourceIsNullException;
import com.chunfeng.service.ex.sourceException.selectException.SelectSourceIsNullException;
import com.chunfeng.service.ex.sourceException.updateException.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学生端业务层实现类
 */
@Service
@Transactional
public class WorkServiceImpl extends ServiceImpl<SubmitWorkMapper, SubmitWork> implements IWorkService {

    /**
     * 提交作业持久层
     */
    @Autowired(required = false)
    private SubmitWorkMapper submitWorkMapper;

    /**
     * 日志持久层
     */
    @Autowired(required = false)
    private ILogService logService;

    /**
     * 时间格式
     */
    @Value("${date.format}")
    private String dateFormat;

    /**
     * 查询所有学生提交的作业
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @Cacheable(value = "submit_work", key = "#current")
    @Override
    public JsonRequest<List<SubmitWork>> selectAllWork(Integer current, Integer size) {
        Page<SubmitWork> workPage = submitWorkMapper.selectPage(new Page<>(current, size), null);//获取所有作业
        if (workPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        List<SubmitWork> works = workPage.getRecords();//获取所有作业
        for (SubmitWork work : works) {
            Log log = logService.selectLogById(work.getLogId()).getData();//拉取日志
            //如果查询到已删除的字段，则跳过此次循环
            if (log.getDeleted() == 1) {
                continue;
            }
            work.setLog(log);//添加
        }
        return new JsonRequest<>(works, workPage.getTotal());
    }

    /**
     * 根据学生编号查询提交的作业
     *
     * @param current   页码
     * @param size      页长
     * @param studentId 学生编号
     * @return JSON
     */
    @Cacheable(value = "submit_work_studentId", key = "#studentId+'_'+#current")
    @Override
    public JsonRequest<List<SubmitWork>> selectWorkByStudentWork(Integer current, Integer size, Long studentId) {
        Page<SubmitWork> workPage = submitWorkMapper.selectPage(
                new Page<>(current, size), //分页
                new LambdaQueryWrapper<SubmitWork>().
                        eq(SubmitWork::getStudentId, studentId));//根据学生id获取所有作业
        if (workPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        List<SubmitWork> works = workPage.getRecords();//获取所有作业
        for (SubmitWork work : works) {
            Log log = logService.selectLogById(work.getLogId()).getData();//拉取日志
            work.setLog(log);//添加
        }
        return new JsonRequest<>(works, workPage.getTotal());
    }

    /**
     * 提交作业
     *
     * @param submitWork 需提供:学生编号,作业编号,作业内容
     * @return JSON
     */
    @CacheEvict(value = {"submit_work", "submit_work_studentId", "submit_work_workId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> addWork(SubmitWork submitWork) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        logService.insertLog(log);//添加日志
        submitWork.setLogId(log.getLogId());//获取已添加的日志id
        int column = submitWorkMapper.insert(submitWork);
        if (column < 1) {
            throw new AddException("添加数据失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 修改已提交的作业
     *
     * @param submitWork 需提供:提交编号,可提供:学生编号,作业编号,作业内容
     * @return JSON
     */
    @CacheEvict(value = {"submit_work", "submit_work_studentId", "submit_work_workId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateWorkById(SubmitWork submitWork) {
        SubmitWork work = submitWorkMapper.selectById(submitWork.getWorkId());//判断作业信息是否存在
        if (work == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        Log log = new Log(work.getLogId(), new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logService.updateLogById(log).getData();//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        int column = submitWorkMapper.updateById(submitWork);
        if (column < 1) {
            throw new UpdateException("修改数据失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 批量删除或恢复已提交的作业
     *
     * @param map <p>
     *            key:提交作业id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @CacheEvict(value = {"submit_work", "submit_work_studentId", "submit_work_workId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteWorkById(Map<Long, Boolean> map) {
        if (map.size() < 1) {
            throw new DeleteSourceIsNullException("删除失败");
        }
        int columns = 0;
        for (Map.Entry<Long, Boolean> entry : map.entrySet()) {
            SubmitWork submitWork = submitWorkMapper.selectById(entry.getKey());
            if (submitWork == null) {
                throw new SelectSourceIsNullException("该数据不存在!");
            }
            int column = logService.updateLogById(new Log(submitWork.getLogId(), entry.getValue() ? 1 : 0,
                    new SimpleDateFormat(dateFormat).format(new Date()))).getData();
            if (column < 1) {
                throw new DeleteException("删除失败!");
            }
            columns += column;
        }
        return new JsonRequest<>(columns);
    }
}

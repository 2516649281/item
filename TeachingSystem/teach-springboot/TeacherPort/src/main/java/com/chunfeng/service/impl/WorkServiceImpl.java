package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.CreateWorkMapper;
import com.chunfeng.entity.CreateWork;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.Log;
import com.chunfeng.service.ILogService;
import com.chunfeng.service.IWorkService;
import com.chunfeng.service.ex.addException.AddException;
import com.chunfeng.service.ex.deleteExcpption.DeleteException;
import com.chunfeng.service.ex.deleteExcpption.DeleteSourceIsNullException;
import com.chunfeng.service.ex.logException.updateException.LogUpdateErrorException;
import com.chunfeng.service.ex.selectException.SelectSourceIsNullException;
import com.chunfeng.service.ex.updateException.UpdateException;
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
 * 教师端业务层实现类
 */
@Service
@Transactional
public class WorkServiceImpl extends ServiceImpl<CreateWorkMapper, CreateWork> implements IWorkService {

    /**
     * 作业持久层
     */
    @Autowired(required = false)
    private CreateWorkMapper createWorkMapper;

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
     * 分页查询已经布置的作业
     *
     * @param current 页码
     * @param size    页数
     * @return JSON
     */
    @Cacheable(value = "create_work_page", key = "#current")
    @Override
    public JsonRequest<List<CreateWork>> selectWork(Integer current, Integer size) {
        Page<CreateWork> workPage = createWorkMapper.selectPage(
                new Page<>(current, size), null);//获取所有作业
        if (workPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        List<CreateWork> works = workPage.getRecords();//获取所有作业
        for (CreateWork work : works) {
            Log log = logService.selectLogById(work.getLogId()).getData();//拉取日志
            work.setLog(log);//添加
        }
        return new JsonRequest<>(works, workPage.getTotal());
    }

    /**
     * 根据教师编号查询作业
     *
     * @param current   页码
     * @param size      每页显示数
     * @param teacherId 教师编号
     * @return JSON
     */
    @Cacheable(value = "create_work_teacherId", key = "#teacherId+'_'+#current")
    @Override
    public JsonRequest<List<CreateWork>> selectAllWorkByTeacherId(Integer current, Integer size, Long teacherId) {
        Page<CreateWork> workPage = createWorkMapper.selectPage(
                new Page<>(current, size), //分页
                new LambdaQueryWrapper<CreateWork>().
                        eq(CreateWork::getTeacherId, teacherId));//根据教师id获取所有作业
        if (workPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        List<CreateWork> works = workPage.getRecords();//获取所有作业
        for (CreateWork work : works) {
            Log log = logService.selectLogById(work.getLogId()).getData();//拉取日志
            work.setLog(log);//添加
        }
        return new JsonRequest<>(works, workPage.getTotal());
    }

    /**
     * 添加作业信息
     *
     * @param createWork 需提供:作业名,作业描述(可以是文件),班级编号,教师编号
     * @return JSON
     */
    @CacheEvict(value = {"create_work_teacherId", "create_work_page", "create_work_classId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> addWork(CreateWork createWork) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        logService.insertLog(log);//添加日志
        createWork.setLogId(log.getLogId());//获取已添加的日志id
        int column = createWorkMapper.insert(createWork);
        if (column < 1) {
            throw new AddException("添加数据失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 修改作业信息
     *
     * @param createWork 需提供:作业编号,可提供:作业名,作业描述(可以是文件),班级编号
     * @return JSON
     */
    @CacheEvict(value = {"create_work_teacherId", "create_work_page", "create_work_classId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateWorkById(CreateWork createWork) {
        CreateWork work = createWorkMapper.selectById(createWork.getWorkId());//判断作业信息是否存在
        if (work == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        Log log = new Log(work.getLogId(),
                new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logService.updateLogById(log).getData();//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        int column = createWorkMapper.updateById(createWork);
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
    @CacheEvict(value = {"create_work_teacherId", "create_work_page", "create_work_classId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteWork(Map<Long, Boolean> map) {
        if (map.size() < 1) {
            throw new DeleteSourceIsNullException("删除失败");
        }
        int columns = 0;
        for (Map.Entry<Long, Boolean> entry : map.entrySet()) {
            CreateWork createWork = createWorkMapper.selectById(entry.getKey());
            if (createWork == null) {
                throw new SelectSourceIsNullException("该数据不存在!");
            }
            int column = logService.updateLogById(new Log(createWork.getLogId(), entry.getValue() ? 1 : 0,
                    new SimpleDateFormat(dateFormat).format(new Date()))).getData();
            if (column < 1) {
                throw new DeleteException("删除失败!");
            }
            columns += column;
        }
        return new JsonRequest<>(columns);
    }
}

package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.LogMapper;
import com.chunfeng.dao.SubjectMapper;
import com.chunfeng.entity.Log;
import com.chunfeng.entity.Subject;
import com.chunfeng.service.ISubjectService;
import com.chunfeng.service.ex.addException.AddException;
import com.chunfeng.service.ex.deleteExcpption.DeleteException;
import com.chunfeng.service.ex.logException.LogAddErrorException;
import com.chunfeng.service.ex.logException.LogSelectErrorException;
import com.chunfeng.service.ex.logException.LogUpdateErrorException;
import com.chunfeng.service.ex.selectException.SelectSourceIsNullException;
import com.chunfeng.service.ex.updateException.UpdateException;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 科目业务层实现类
 */
@Service
@Transactional
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    /**
     * 科目持久层
     */
    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 日志持久层
     */
    @Autowired
    private LogMapper logMapper;


    /**
     * 时间格式
     */
    @Value("${date.format}")
    private String dateFormat;

    /**
     * 查询所有科目
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @Cacheable(value = "subject_page", key = "#current")
    @Override
    public JsonRequest<List<Subject>> selectAllSubject(Integer current, Integer size) {
        Page<Subject> subjectPage = subjectMapper.selectPage(new Page<>(current, size), null);//获取所有科目
        if (subjectPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        Long pageSize = subjectPage.getTotal();
        List<Subject> classList = subjectPage.getRecords();//获取所有科目
        for (Subject subject : classList) {
            Log log = logMapper.selectById(subject.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            subject.setLog(log);//添加
        }
        return new JsonRequest<>(200, "", classList, pageSize);
    }

    /**
     * 根据科目名查询
     *
     * @param current     页码
     * @param size        页长
     * @param subjectName 科目名
     * @return JSON
     */
    @Cacheable(value = "subject_name", key = "#subjectName+'_'+#current")
    @Override
    public JsonRequest<List<Subject>> selectAllLikeName(Integer current, Integer size, String subjectName) {
        Page<Subject> subjectPage = subjectMapper.selectPage(new Page<>(current, size), new LambdaQueryWrapper<Subject>()
                .like(Subject::getSubjectName, subjectName));//根据科目名查询
        if (subjectPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        Long pageSize = subjectPage.getTotal();
        List<Subject> classList = subjectPage.getRecords();//获取所有科目
        for (Subject subject : classList) {
            Log log = logMapper.selectById(subject.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            subject.setLog(log);//添加
        }
        return new JsonRequest<>(200, "", classList, pageSize);
    }

    /**
     * 根据科目编号查询
     *
     * @param subjectId 科目编号
     * @return JSON
     */
    @Override
    public JsonRequest<Subject> selectAllById(Long subjectId) {
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        return new JsonRequest<>(200, "", subject, null);
    }

    /**
     * 添加科目
     *
     * @param subject 需提供:科目名
     * @return JSON
     */
    @CacheEvict(value = {"subject_page", "subject_name"}, allEntries = true)
    @Override
    public JsonRequest<Integer> addSubject(Subject subject) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        int logColumn = logMapper.insert(log);//添加日志
        if (logColumn < 1) {
            throw new LogAddErrorException("拉取日志失败!");
        }
        subject.setLogId(log.getLogId());//获取已添加的日志id
        int column = subjectMapper.insert(subject);
        if (column < 1) {
            throw new AddException("添加数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 修改科目
     *
     * @param subject 需提供:科目编号,可提供:科目名
     * @return JSON
     */
    @CacheEvict(value = {"subject_page", "subject_name"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateSubjectById(Subject subject) {
        Subject subjectSource = subjectMapper.selectById(subject.getSubjectId());//判断科目信息是否存在
        if (subjectSource == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        Log log = new Log(subjectSource.getLogId(),
                new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logMapper.updateById(log);//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        int column = subjectMapper.updateById(subject);
        if (column < 1) {
            throw new UpdateException("修改数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 删除或恢复科目
     *
     * @param subjectId 科目编号
     * @param index     操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @CacheEvict(value = {"subject_page", "subject_name"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteSubjectById(Long subjectId, Boolean index) {
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        int column = logMapper.updateById(new Log(subject.getLogId(), index ? 1 : 0,//修改日志状态
                new SimpleDateFormat(dateFormat).format(new Date())));//修改日志时间
        if (column < 1) {
            throw new DeleteException("删除失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }
}

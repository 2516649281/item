package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.SubjectMapper;
import com.chunfeng.dao.TeacherMapper;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.Log;
import com.chunfeng.entity.Subject;
import com.chunfeng.entity.Teacher;
import com.chunfeng.service.ILogService;
import com.chunfeng.service.ISubjectService;
import com.chunfeng.service.ex.addException.AddException;
import com.chunfeng.service.ex.deleteExcpption.DeleteException;
import com.chunfeng.service.ex.deleteExcpption.DeleteSourceErrorException;
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
 * 科目业务层实现类
 */
@Service
@Transactional
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    /**
     * 科目持久层
     */
    @Autowired(required = false)
    private SubjectMapper subjectMapper;

    /**
     * 日志业务层
     */
    @Autowired(required = false)
    private ILogService logService;

    /**
     * 教师持久层
     */
    @Autowired(required = false)
    private TeacherMapper teacherMapper;

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
            Log log = logService.selectLogById(subject.getLogId()).getData();//拉取日志
            subject.setLog(log);//添加
        }
        return new JsonRequest<>(classList, pageSize);
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
            Log log = logService.selectLogById(subject.getLogId()).getData();//拉取日志
            subject.setLog(log);//添加
        }
        return new JsonRequest<>(classList, pageSize);
    }

    /**
     * 根据科目编号查询
     *
     * @param subjectId 科目编号
     * @return JSON
     */
    @Cacheable(value = "subject_id", key = "#subjectId")
    @Override
    public JsonRequest<Subject> selectAllById(Long subjectId) {
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        subject.setLog(logService.selectLogById(subject.getLogId()).getData());
        return new JsonRequest<>(subject, null);
    }

    /**
     * 添加科目
     *
     * @param subject 需提供:科目名
     * @return JSON
     */
    @CacheEvict(value = {"subject_page", "subject_name", "subject_id"}, allEntries = true)
    @Override
    public JsonRequest<Integer> addSubject(Subject subject) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        logService.insertLog(log);
        subject.setLogId(log.getLogId());//获取已添加的日志id
        int column = subjectMapper.insert(subject);
        if (column < 1) {
            throw new AddException("添加数据失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 修改科目
     *
     * @param subject 需提供:科目编号,可提供:科目名
     * @return JSON
     */
    @CacheEvict(value = {"subject_page", "subject_name", "subject_id"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateSubjectById(Subject subject) {
        Subject subjectSource = subjectMapper.selectById(subject.getSubjectId());//判断科目信息是否存在
        if (subjectSource == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        Log log = new Log(subjectSource.getLogId(),
                new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logService.updateLogById(log).getData();//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        int column = subjectMapper.updateById(subject);
        if (column < 1) {
            throw new UpdateException("修改数据失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 批量删除或恢复科目
     *
     * @param map <p>
     *            key:科目id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @CacheEvict(value = {"subject_page", "subject_name", "subject_id"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteSubjectById(Map<Long, Boolean> map) {
        if (map.size() < 1) {
            throw new DeleteSourceIsNullException("删除失败");
        }
        int columns = 0;
        for (Map.Entry<Long, Boolean> entry : map.entrySet()) {
            Subject subject = subjectMapper.selectById(entry.getKey());
            if (subject == null) {
                throw new SelectSourceIsNullException("该数据不存在!");
            }
            //查询该科目中是否存在教师
            List<Teacher> students = teacherMapper.selectList(
                    new LambdaQueryWrapper<Teacher>()
                            .eq(Teacher::getSubjectId, entry.getKey()));//匹配班级编号
            if (students.size() > 0) {
                throw new DeleteSourceErrorException("该科目存在教师,不可更改!");
            }
            int column = logService.updateLogById(new Log(subject.getLogId(), entry.getValue() ? 1 : 0,//修改日志状态
                    new SimpleDateFormat(dateFormat).format(new Date()))).getData();//修改日志时间
            if (column < 1) {
                throw new DeleteException("删除失败!");
            }
            columns += column;
        }
        return new JsonRequest<>(columns);
    }
}

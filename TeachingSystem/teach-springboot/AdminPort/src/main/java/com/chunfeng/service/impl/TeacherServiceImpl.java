package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.LogMapper;
import com.chunfeng.dao.TeacherMapper;
import com.chunfeng.entity.Log;
import com.chunfeng.entity.Teacher;
import com.chunfeng.service.ITeacherService;
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
 * 教师管理业务层实现类
 */
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    /**
     * 教师持久层
     */
    @Autowired
    private TeacherMapper teacherMapper;


    /**
     * 时间格式
     */
    @Value("${date.format}")
    private String dateFormat;

    /**
     * 日志持久层
     */
    @Autowired
    private LogMapper logMapper;

    /**
     * 查询所有教师
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @Cacheable(value = "teacher_page", key = "#current")
    @Override
    public JsonRequest<List<Teacher>> selectAllTeacher(Integer current, Integer size) {
        Page<Teacher> teacherPage = teacherMapper.selectPage(
                new Page<>(current, size), null);//获取所有教师
        if (teacherPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        Long pageSize = teacherPage.getTotal();
        List<Teacher> teacherList = teacherPage.getRecords();//获取所有教师
        for (Teacher teacher : teacherList) {
            Log log = logMapper.selectById(teacher.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            teacher.setLog(log);//添加
        }
        return new JsonRequest<>(200, "", teacherList, pageSize);
    }

    /**
     * 按照科目编号查询教师
     *
     * @param current   页码
     * @param size      页长
     * @param subjectId 科目编号
     * @return JSON
     */
    @Cacheable(value = "teacher_subjectId", key = "#subjectId+'_'+#current")
    @Override
    public JsonRequest<List<Teacher>> selectAllBySubject(Integer current, Integer size, Long subjectId) {
        Page<Teacher> teacherPage = teacherMapper.selectPage(
                new Page<>(current, size), //分页
                new LambdaQueryWrapper<Teacher>().
                        eq(Teacher::getSubjectId, subjectId));//根据科目编号获取所有教师
        if (teacherPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        Long pageSize = teacherPage.getTotal();
        List<Teacher> teacherList = teacherPage.getRecords();//获取所有教师
        for (Teacher teacher : teacherList) {
            Log log = logMapper.selectById(teacher.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            teacher.setLog(log);//添加
        }
        return new JsonRequest<>(200, "", teacherList, pageSize);
    }

    /**
     * 根据编号查询教师
     *
     * @param teacherId 教师编号
     * @return JSON
     */
    @Override
    public JsonRequest<Teacher> selectAllById(Long teacherId) {
        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        return new JsonRequest<>(200, "", teacher, null);
    }

    /**
     * 添加教师
     *
     * @param teacher 需提供:教师姓名,教师年龄,教师性别,教师住址,教师电话,教师邮箱,科目编号
     * @return JSON
     */
    @CacheEvict(value = {"teacher_page", "teacher_subjectId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> addTeacher(Teacher teacher) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        int logColumn = logMapper.insert(log);//添加日志
        if (logColumn < 1) {
            throw new LogAddErrorException("拉取日志失败!");
        }
        teacher.setLogId(log.getLogId());//获取已添加的日志id
        int column = teacherMapper.insert(teacher);
        if (column < 1) {
            throw new AddException("添加数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 修改教师
     *
     * @param teacher 需提供:教师编号,可提供:教师姓名,教师年龄,教师性别,教师住址,教师电话,教师邮箱,科目编号
     * @return JSON
     */
    @CacheEvict(value = {"teacher_page", "teacher_subjectId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateTeacher(Teacher teacher) {
        Teacher teacherSource = teacherMapper.selectById(teacher.getTeacherId());//判断教师信息是否存在
        if (teacherSource == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        Log log = new Log(teacherSource.getLogId(),
                new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logMapper.updateById(log);//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        int column = teacherMapper.updateById(teacher);
        if (column < 1) {
            throw new UpdateException("修改数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 删除或恢复教师
     *
     * @param teacherId 教师编号
     * @param index     操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @CacheEvict(value = {"teacher_page", "teacher_subjectId"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteTeacher(Integer teacherId, Boolean index) {
        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        int column = logMapper.updateById(new Log(teacher.getLogId(), index ? 1 : 0,
                new SimpleDateFormat(dateFormat).format(new Date())));
        if (column < 1) {
            throw new DeleteException("删除失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }
}

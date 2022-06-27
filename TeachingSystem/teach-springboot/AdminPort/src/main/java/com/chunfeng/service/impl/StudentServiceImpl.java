package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.LogMapper;
import com.chunfeng.dao.StudentMapper;
import com.chunfeng.entity.Log;
import com.chunfeng.entity.Student;
import com.chunfeng.service.IStudentService;
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
 * 学生管理业务层实现类
 */
@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    /**
     * 学生持久层
     */
    @Autowired
    private StudentMapper studentMapper;

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
     * 查询所有学生
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @Cacheable(value = "student_page", key = "#current")
    @Override
    public JsonRequest<List<Student>> selectAllStudent(Integer current, Integer size) {
        Page<Student> studentPage = studentMapper.selectPage(new Page<>(current, size), null);//获取所有学生
        if (studentPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        List<Student> studentList = studentPage.getRecords();//获取所有学生
        for (Student student : studentList) {
            Log log = logMapper.selectById(student.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            student.setLog(log);//添加
        }
        long pageSize = studentPage.getTotal();
        return new JsonRequest<>(200, "", studentList, pageSize);
    }

    /**
     * 按照班级编号查询学生
     *
     * @param current 页码
     * @param size    页长
     * @param classId 班级编号
     * @return JSON
     */
    @Cacheable(value = "student_class", key = "#classId+'_'+#current")
    @Override
    public JsonRequest<List<Student>> selectAllStudentByClassId(Integer current, Integer size, Long classId) {
        Page<Student> studentPage = studentMapper.selectPage(
                new Page<>(current, size), //分页
                new LambdaQueryWrapper<Student>().
                        eq(Student::getClassId, classId));//根据班级id获取所有学生
        if (studentPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        List<Student> studentList = studentPage.getRecords();//获取所有学生
        for (Student student : studentList) {
            Log log = logMapper.selectById(student.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            student.setLog(log);//添加
        }
        return new JsonRequest<>(200, "", studentList, studentPage.getTotal());
    }

    /**
     * 根据编号查询学生
     *
     * @param studentId 学生编号
     * @return JSON
     */
    @Override
    public JsonRequest<Student> selectAllStudentById(Long studentId) {
        Student student = studentMapper.selectById(studentId);
        if (student == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        return new JsonRequest<>(200, "", student, null);
    }

    /**
     * 添加学生
     *
     * @param student 需提供:学生姓名,学生年龄,学生性别,学生住址,学生电话,学生邮箱,班级编号
     * @return JSON
     */
    @CacheEvict(value = {"student_page", "student_class"}, allEntries = true)
    @Override
    public JsonRequest<Integer> addStudent(Student student) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        int logColumn = logMapper.insert(log);//添加日志
        if (logColumn < 1) {
            throw new LogAddErrorException("拉取日志失败!");
        }
        student.setLogId(log.getLogId());//获取已添加的日志id
        int column = studentMapper.insert(student);
        if (column < 1) {
            throw new AddException("添加数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 修改学生
     *
     * @param student 需提供:学生编号,可提供:学生姓名,学生年龄,学生性别,学生住址,学生电话,学生邮箱,班级编号
     * @return JSON
     */
    @CacheEvict(value = {"student_page", "student_class"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateStudentById(Student student) {
        Student studentSource = studentMapper.selectById(student.getStudentId());//判断学生信息是否存在
        if (studentSource == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        Log log = new Log(studentSource.getLogId(),
                new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logMapper.updateById(log);//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        int column = studentMapper.updateById(student);
        if (column < 1) {
            throw new UpdateException("修改数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 删除或恢复学生
     *
     * @param studentId 学生编号
     * @param index     操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @CacheEvict(value = {"student_page", "student_class"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteStudentById(Long studentId, Boolean index) {
        Student student = studentMapper.selectById(studentId);
        if (student == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        int column = logMapper.updateById(new Log(student.getLogId(), index ? 1 : 0,
                new SimpleDateFormat(dateFormat).format(new Date())));
        if (column < 1) {
            throw new DeleteException("删除失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }
}

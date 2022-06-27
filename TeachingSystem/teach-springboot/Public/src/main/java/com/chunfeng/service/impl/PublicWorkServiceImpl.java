package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.*;
import com.chunfeng.entity.Class;
import com.chunfeng.entity.*;
import com.chunfeng.service.IPublicWorkService;
import com.chunfeng.service.ex.selectException.SelectSourceIsNullException;
import com.chunfeng.service.ex.updateException.UpdateException;
import com.chunfeng.service.ex.updateException.UpdateSourceIsNullException;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生端与教师端公共业务层
 */
@Service
public class PublicWorkServiceImpl extends ServiceImpl<SubmitWorkMapper, SubmitWork> implements IPublicWorkService {

    /**
     * 学生持久层
     */
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 提交作业持久层
     */
    @Autowired
    private SubmitWorkMapper submitWorkMapper;

    /**
     * 创建作业持久层
     */
    @Autowired
    private CreateWorkMapper createWorkMapper;

    /**
     * 日志持久层
     */
    @Autowired
    private LogMapper logMapper;

    /**
     * 班级持久层
     */
    @Autowired
    private ClassMapper classMapper;

    /**
     * 根据班级编号查询学生
     *
     * @param classId 班级编号
     * @return JSON
     */
    @Cacheable(value = "student_class", key = "#classId")
    @Override
    public JsonRequest<List<Student>> selectAllStudent(Long classId) {
        List<Student> students = studentMapper.selectList(new LambdaQueryWrapper<Student>()
                .eq(Student::getClassId, classId));
        if (students == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        return new JsonRequest<>(200, "", students, (long) students.size());
    }

    /**
     * 教师端根据作业编号查询已提交的作业
     *
     * @param workId 作业编号
     * @return JSON
     */
    @Cacheable(value = "submit_work_workId", key = "#workId")
    @Override
    public JsonRequest<List<SubmitWork>> selectAllSubmit(Long workId) {
        List<SubmitWork> submitWorks = submitWorkMapper.selectList(new LambdaQueryWrapper<SubmitWork>()
                .eq(SubmitWork::getWorkId, workId));
        if (submitWorks == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        for (int i = 0; i < submitWorks.size(); i++) {
            submitWorks.get(i).setStudent(studentMapper.selectById(submitWorks.get(i).getStudentId()));//带入学生信息
            Log log = logMapper.selectById(submitWorks.get(i).getLogId());//拉取日志
            //如果该学生的作业已经删除，则移除此集合
            if (log.getDeleted() == 1) {
                submitWorks.remove(i);
                continue;
            }
            submitWorks.get(i).setLog(log);//添加
        }
        return new JsonRequest<>(200, "", submitWorks, (long) submitWorks.size());
    }

    /**
     * 教师端批改学生作业
     *
     * @param submitWork 需提供:编号,成绩
     * @return JSON
     */
    @CacheEvict(value = "submit_work_workId", allEntries = true)
    @Override
    public JsonRequest<Integer> updateAllSubmit(SubmitWork submitWork) {
        SubmitWork selectSubmitWork = submitWorkMapper.selectById(submitWork.getSubmitId());
        if (logMapper.selectById(selectSubmitWork.getLogId()).getDeleted() == 1) {//判断学生提交的作业是否已经删除
            throw new UpdateSourceIsNullException("该作业已删除!");
        }
        int column = submitWorkMapper.updateById(submitWork);
        if (column < 1) {
            throw new UpdateException("批改作业失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 学生端根据班级编号查询教师布置的作业
     *
     * @param classId 班级编号
     * @return JSON
     */
    @Cacheable(value = "create_work_classId", key = "#classId")
    @Override
    public JsonRequest<List<CreateWork>> selectAllCreate(Long classId) {
        List<CreateWork> createWorks = createWorkMapper.selectList(new LambdaQueryWrapper<CreateWork>()
                .eq(CreateWork::getClassId, classId));//班级查找
        if (createWorks == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        for (int i = 0; i < createWorks.size(); i++) {
            //拉取日志信息
            Log log = logMapper.selectById(createWorks.get(i).getLogId());
            //如果教师布置的作业已经删除，则移除此集合
            if (log.getDeleted() == 1) {
                createWorks.remove(i);
                continue;
            }
            createWorks.get(i).setLog(log);
        }
        return new JsonRequest<>(200, "", createWorks, (long) createWorks.size());
    }

    /**
     * 教师端查询所有班级
     *
     * @return JSON
     */
    @Cacheable(value = "class_all")
    @Override
    public JsonRequest<List<Class>> selectAllClassName() {
        List<Class> classes = classMapper.selectList(null);
        if (classes == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        return new JsonRequest<>(200, "", classes, (long) classes.size());
    }
}

package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.Class;
import com.chunfeng.entity.*;

import java.util.List;

/**
 * 学生端和教师端通用服务层接口
 */
public interface IPublicWorkService extends IService<SubmitWork> {


    /**
     * 根据班级编号查询学生
     *
     * @param classId 班级编号
     * @return JSON
     */
    JsonRequest<List<Student>> selectAllStudent(Long classId);

    /**
     * 教师端根据作业编号查询已提交的作业
     *
     * @param workId 作业编号
     * @return JSON
     */
    JsonRequest<List<SubmitWork>> selectAllSubmit(Long workId);

    /**
     * 教师端批改学生作业
     *
     * @param submitWork 需提供:编号,成绩
     * @return JSON
     */
    JsonRequest<Integer> updateAllSubmit(SubmitWork submitWork);

    /**
     * 学生端根据班级编号查询教师布置的作业
     *
     * @param classId 班级编号
     * @return JSON
     */
    JsonRequest<List<CreateWork>> selectAllCreate(Long classId);

    /**
     * 教师端查询所有班级
     *
     * @return JSON
     */
    JsonRequest<List<Class>> selectAllClassName();
}

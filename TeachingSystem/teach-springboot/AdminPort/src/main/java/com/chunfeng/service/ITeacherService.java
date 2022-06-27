package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.Teacher;
import com.chunfeng.util.JsonRequest;

import java.util.List;

/**
 * 教师管理业务层接口
 */
public interface ITeacherService extends IService<Teacher> {

    /**
     * 查询所有教师
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    JsonRequest<List<Teacher>> selectAllTeacher(Integer current, Integer size);

    /**
     * 按照科目编号查询教师
     *
     * @param current   页码
     * @param size      页长
     * @param subjectId 科目编号
     * @return JSON
     */
    JsonRequest<List<Teacher>> selectAllBySubject(Integer current, Integer size, Long subjectId);

    /**
     * 根据编号查询教师
     *
     * @param teacherId 教师编号
     * @return JSON
     */
    JsonRequest<Teacher> selectAllById(Long teacherId);

    /**
     * 添加教师
     *
     * @param teacher 需提供:教师姓名,教师年龄,教师性别,教师住址,教师电话,教师邮箱,科目编号
     * @return JSON
     */
    JsonRequest<Integer> addTeacher(Teacher teacher);

    /**
     * 修改教师
     *
     * @param teacher 需提供:教师编号,可提供:教师姓名,教师年龄,教师性别,教师住址,教师电话,教师邮箱,科目编号
     * @return JSON
     */
    JsonRequest<Integer> updateTeacher(Teacher teacher);

    /**
     * 删除或恢复教师
     *
     * @param teacherId 教师编号
     * @param index     操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    JsonRequest<Integer> deleteTeacher(Integer teacherId, Boolean index);
}

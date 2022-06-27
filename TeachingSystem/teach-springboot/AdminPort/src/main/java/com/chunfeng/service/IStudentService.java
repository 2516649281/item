package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.Student;
import com.chunfeng.util.JsonRequest;

import java.util.List;

/**
 * 学生业务层接口
 */
public interface IStudentService extends IService<Student> {

    /**
     * 查询所有学生
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    JsonRequest<List<Student>> selectAllStudent(Integer current, Integer size);

    /**
     * 按照班级编号查询学生
     *
     * @param current 页码
     * @param size    页长
     * @param classId 班级编号
     * @return JSON
     */
    JsonRequest<List<Student>> selectAllStudentByClassId(Integer current, Integer size, Long classId);

    /**
     * 根据编号查询学生
     *
     * @param studentId 学生编号
     * @return JSON
     */
    JsonRequest<Student> selectAllStudentById(Long studentId);

    /**
     * 添加学生
     *
     * @param student 需提供:学生姓名,学生年龄,学生性别,学生住址,学生电话,学生邮箱,班级编号
     * @return JSON
     */
    JsonRequest<Integer> addStudent(Student student);

    /**
     * 修改学生
     *
     * @param student 需提供:学生编号,可提供:学生姓名,学生年龄,学生性别,学生住址,学生电话,学生邮箱,班级编号
     * @return JSON
     */
    JsonRequest<Integer> updateStudentById(Student student);

    /**
     * 删除或恢复学生
     *
     * @param studentId 学生编号
     * @param index     操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    JsonRequest<Integer> deleteStudentById(Long studentId, Boolean index);
}

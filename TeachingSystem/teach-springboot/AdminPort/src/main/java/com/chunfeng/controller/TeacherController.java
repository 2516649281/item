package com.chunfeng.controller;

import com.chunfeng.entity.Teacher;
import com.chunfeng.service.ITeacherService;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师控制层
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController extends ServiceController {

    /**
     * 教师持久层
     */
    @Autowired
    private ITeacherService teacherService;

    /**
     * 查询所有教师
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @GetMapping("/{current}/{size}")
    JsonRequest<List<Teacher>> selectAllTeacher(@PathVariable Integer current, @PathVariable Integer size) {
        return teacherService.selectAllTeacher(current, size);
    }

    /**
     * 按照科目编号查询教师
     *
     * @param current   页码
     * @param size      页长
     * @param subjectId 科目编号
     * @return JSON
     */
    @GetMapping("/{current}/{size}/{subjectId}")
    JsonRequest<List<Teacher>> selectAlBySubjectId(@PathVariable Integer current, @PathVariable Integer size, @PathVariable Long subjectId) {
        return teacherService.selectAllBySubject(current, size, subjectId);
    }

    /**
     * 根据编号查询教师
     *
     * @param teacherId 教师编号
     * @return JSON
     */
    @GetMapping("/{teacherId}")
    JsonRequest<Teacher> selectAllTeacherById(@PathVariable Long teacherId) {
        return teacherService.selectAllById(teacherId);
    }

    /**
     * 添加教师
     *
     * @param teacher 需提供:教师姓名,教师年龄,教师性别,教师住址,教师电话,教师邮箱,科目编号
     * @return JSON
     */
    @PostMapping
    JsonRequest<Integer> addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }

    /**
     * 修改教师
     *
     * @param teacher 需提供:教师编号,可提供:教师姓名,教师年龄,教师性别,教师住址,教师电话,教师邮箱,科目编号
     * @return JSON
     */
    @PutMapping
    JsonRequest<Integer> updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.updateTeacher(teacher);
    }

    /**
     * 删除或恢复教师
     *
     * @param teacherId 教师编号
     * @param index     操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @DeleteMapping("/{teacherId}/{index}")
    JsonRequest<Integer> deleteTeacher(@PathVariable Integer teacherId, @PathVariable Boolean index) {
        return teacherService.deleteTeacher(teacherId, index);
    }
}

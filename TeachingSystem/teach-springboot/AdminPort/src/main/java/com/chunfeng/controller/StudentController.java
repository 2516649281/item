package com.chunfeng.controller;

import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.Student;
import com.chunfeng.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 学生管理控制层
 */
@RestController
@RequestMapping("/student")
public class StudentController extends ServiceController {

    /**
     * 学生业务层
     */
    @Autowired(required = false)
    private IStudentService studentService;

    /**
     * 查询所有学生
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @GetMapping("/{current}/{size}")
    JsonRequest<List<Student>> selectAllStudent(@PathVariable Integer current, @PathVariable Integer size) {
        return studentService.selectAllStudent(current, size);
    }

    /**
     * 按照班级编号查询学生
     *
     * @param current 页码
     * @param size    页长
     * @param classId 班级编号
     * @return JSON
     */
    @GetMapping("/{current}/{size}/{classId}")
    JsonRequest<List<Student>> selectAllStudentByClassId(@PathVariable Integer current, @PathVariable Integer size, @PathVariable Long classId) {
        return studentService.selectAllStudentByClassId(current, size, classId);
    }

    /**
     * 根据编号查询学生
     *
     * @param studentId 学生编号
     * @return JSON
     */
    @GetMapping("/{studentId}")
    JsonRequest<Student> selectAllStudentById(@PathVariable Long studentId) {
        return studentService.selectAllStudentById(studentId);
    }

    /**
     * 添加学生
     *
     * @param student 需提供:学生姓名,学生年龄,学生性别,学生住址,学生电话,学生邮箱,班级编号
     * @return JSON
     */
    @PostMapping
    JsonRequest<Integer> addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    /**
     * 修改学生
     *
     * @param student 需提供:学生编号,可提供:学生姓名,学生年龄,学生性别,学生住址,学生电话,学生邮箱,班级编号
     * @return JSON
     */
    @PutMapping
    JsonRequest<Integer> updateStudentById(@RequestBody Student student) {
        return studentService.updateStudentById(student);
    }

    /**
     * 批量删除或恢复学生
     *
     * @param map <p>
     *            key:学生id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @DeleteMapping
    JsonRequest<Integer> deleteStudentById(@RequestBody Map<Long, Boolean> map) {
        return studentService.deleteStudentById(map);
    }
}

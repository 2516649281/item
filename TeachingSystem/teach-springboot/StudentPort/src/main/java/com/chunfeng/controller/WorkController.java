package com.chunfeng.controller;

import com.chunfeng.entity.CreateWork;
import com.chunfeng.entity.Student;
import com.chunfeng.entity.SubmitWork;
import com.chunfeng.service.IPublicWorkService;
import com.chunfeng.service.impl.WorkServiceImpl;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生端控制层
 */
@RestController
@RequestMapping("/work")
public class WorkController extends ServiceController {

    /**
     * 作业提交业务层
     */
    @Autowired
    private WorkServiceImpl workService;

    /**
     * 公共业务层
     */
    @Autowired
    private IPublicWorkService publicWorkService;

    /**
     * 分页查询已经提交的作业
     *
     * @param current 页码
     * @param size    页数
     * @return JSON
     */
    @GetMapping("/{current}/{size}")
    JsonRequest<List<SubmitWork>> selectAllWork(@PathVariable Integer current, @PathVariable Integer size) {
        return workService.selectAllWork(current, size);
    }

    /**
     * 根据学生编号查询提交的作业
     *
     * @param current   页码
     * @param size      页长
     * @param studentId 学生编号
     * @return JSON
     */
    @GetMapping("/{current}/{size}/{studentId}")
    JsonRequest<List<SubmitWork>> selectAllByStudentId(@PathVariable Integer current, @PathVariable Integer size, @PathVariable Long studentId) {
        return workService.selectWorkByStudentWork(current, size, studentId);
    }

    /**
     * 提交作业
     *
     * @param submitWork 需提供:学生编号,作业编号,作业内容
     * @return JSON
     */
    @PostMapping
    JsonRequest<Integer> addWork(@RequestBody SubmitWork submitWork) {
        return workService.addWork(submitWork);
    }

    /**
     * 修改已提交的作业
     *
     * @param submitWork 需提供:提交编号,可提供:学生编号,作业编号,作业内容
     * @return JSON
     */
    @PutMapping
    JsonRequest<Integer> updateWorkById(@RequestBody SubmitWork submitWork) {
        return workService.updateWorkById(submitWork);
    }

    /**
     * 删除已提交的作业
     *
     * @param submitId 需提供:提交编号
     * @param index    操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @DeleteMapping("/{submitId}/{index}")
    JsonRequest<Integer> deleteWorkById(@PathVariable Long submitId, @PathVariable Boolean index) {
        return workService.deleteWorkById(submitId, index);
    }

    /**
     * 根据班级编号查询学生
     *
     * @param classId 班级编号
     * @return JSON
     */
    @GetMapping(value = "/{classId}", params = "student")
    JsonRequest<List<Student>> selectAllStudentByClass(@PathVariable Long classId) {
        return publicWorkService.selectAllStudent(classId);
    }

    /**
     * 根据班级编号查询教师布置的作业
     *
     * @param classId 班级编号
     * @return JSON
     */
    @GetMapping(value = "/{classId}", params = "work")
    JsonRequest<List<CreateWork>> selectAllCreate(@PathVariable Long classId) {
        return publicWorkService.selectAllCreate(classId);
    }
}

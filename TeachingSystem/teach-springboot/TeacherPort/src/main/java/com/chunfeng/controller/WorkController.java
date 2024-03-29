package com.chunfeng.controller;

import com.chunfeng.entity.Class;
import com.chunfeng.entity.*;
import com.chunfeng.service.IPublicWorkService;
import com.chunfeng.service.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 教师端
 */
@RestController
@RequestMapping("/work")
public class WorkController extends ServiceController {

    /**
     * 作业业务层
     */
    @Autowired(required = false)
    private IWorkService workService;

    /**
     * 公共业务层
     */
    @Autowired(required = false)
    private IPublicWorkService publicWorkService;

    /**
     * 分页查询已经布置的作业
     *
     * @param current 页码
     * @param size    页数
     * @return JSON
     */
    @GetMapping("/{current}/{size}")
    JsonRequest<List<CreateWork>> selectAllWork(@PathVariable Integer current, @PathVariable Integer size) {
        return workService.selectWork(current, size);
    }

    /**
     * 根据教师编号查询作业
     *
     * @param current   页码
     * @param size      每页显示数
     * @param teacherId 教师编号
     * @return JSON
     */
    @GetMapping("/{current}/{size}/{teacherId}")
    JsonRequest<List<CreateWork>> selectAllByTeacherId(@PathVariable Integer current, @PathVariable Integer size, @PathVariable Long teacherId) {
        return workService.selectAllWorkByTeacherId(current, size, teacherId);
    }

    /**
     * 添加作业信息
     *
     * @param createWork 需提供:作业名,作业描述(可以是文件),班级编号,教师编号
     * @return JSON
     */
    @PostMapping
    JsonRequest<Integer> addWork(@RequestBody CreateWork createWork) {
        return workService.addWork(createWork);
    }

    /**
     * 修改作业信息
     *
     * @param createWork 需提供:作业编号,可提供:作业名,作业描述(可以是文件),班级编号
     * @return JSON
     */
    @PutMapping
    JsonRequest<Integer> updateWorkById(@RequestBody CreateWork createWork) {
        return workService.updateWorkById(createWork);
    }

    /**
     * 批量删除或恢复已提交的作业
     *
     * @param map <p>
     *            key:提交作业id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @DeleteMapping
    JsonRequest<Integer> deleteWorkById(@RequestBody Map<Long, Boolean> map) {
        return workService.deleteWork(map);
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
     * 教师端根据作业编号查询已提交的作业
     *
     * @param workId 作业编号
     * @return JSON
     */
    @GetMapping(value = "/{workId}", params = "sw")
    JsonRequest<List<SubmitWork>> selectAllSubmit(@PathVariable Long workId) {
        return publicWorkService.selectAllSubmit(workId);
    }

    /**
     * 根据班级编号查询教师布置的作业
     *
     * @param classId 班级编号
     * @return JSON
     */
    @GetMapping(value = "/{classId}", params = "work")
    JsonRequest<List<SubmitWork>> selectAllCreate(@PathVariable Long classId) {
        return publicWorkService.selectAllSubmit(classId);
    }

    /**
     * 教师端批改学生作业
     *
     * @param submitWork 需提供:编号,成绩
     * @return JSON
     */
    @PutMapping("/sc")
    JsonRequest<Integer> updateAllSubmit(@RequestBody SubmitWork submitWork) {
        return publicWorkService.updateAllSubmit(submitWork);
    }

    /**
     * 教师端查询所有班级
     *
     * @return JSON
     */
    @GetMapping(params = "class")
    JsonRequest<List<Class>> selectAllClass() {
        return publicWorkService.selectAllClassName();
    }
}

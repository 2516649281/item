package com.chunfeng.controller;

import com.chunfeng.entity.Class;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 班级控制层
 */
@RestController
@RequestMapping("/class")
public class ClassController extends ServiceController {

    /**
     * 班级业务层
     */
    @Autowired(required = false)
    private IClassService classService;

    /**
     * 查询所有班级
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @GetMapping("/{current}/{size}")
    JsonRequest<List<Class>> selectAllClass(@PathVariable Integer current, @PathVariable Integer size) {
        return classService.selectAllClass(current, size);
    }

    /**
     * 根据班级名查询所有班级
     *
     * @param current   页码
     * @param size      页长
     * @param className 班级名称
     * @return JSON
     */
    @GetMapping("/{current}/{size}/{className}")
    JsonRequest<List<Class>> selectAllClassLikeName(@PathVariable Integer current, @PathVariable Integer size, @PathVariable String className) {
        return classService.selectAllClassLikeName(current, size, className);
    }

    /**
     * 根据班级编号查询班级
     *
     * @param classId 班级编号
     * @return JSON
     */
    @GetMapping("/{classId}")
    JsonRequest<Class> selectAllClassById(@PathVariable Long classId) {
        return classService.selectAllClassById(classId);
    }

    /**
     * 添加班级
     *
     * @param aClass 需提供:班级名称
     * @return JSON
     */
    @PostMapping
    JsonRequest<Integer> addClass(@RequestBody Class aClass) {
        return classService.addClass(aClass);
    }

    /**
     * 修改班级
     *
     * @param aClass 需提供:班级编号,可提供:班级名称
     * @return JSON
     */
    @PutMapping
    JsonRequest<Integer> updateClassById(@RequestBody Class aClass) {
        return classService.updateClassById(aClass);
    }

    /**
     * 批量删除或恢复班级
     *
     * @param map <p>
     *            key:班级id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @DeleteMapping
    JsonRequest<Integer> deleteClassById(@RequestBody Map<Long, Boolean> map) {
        return classService.deleteClassById(map);
    }
}

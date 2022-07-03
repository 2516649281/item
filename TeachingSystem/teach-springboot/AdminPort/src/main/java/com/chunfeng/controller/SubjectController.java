package com.chunfeng.controller;

import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.Subject;
import com.chunfeng.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 科目控制层
 */
@RestController
@RequestMapping("/subject")
public class SubjectController extends ServiceController {

    /**
     * 科目业务层
     */
    @Autowired(required = false)
    private ISubjectService subjectService;

    /**
     * 查询所有科目
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @GetMapping("/{current}/{size}")
    JsonRequest<List<Subject>> selectAllSubject(@PathVariable Integer current, @PathVariable Integer size) {
        return subjectService.selectAllSubject(current, size);
    }

    /**
     * 根据科目名查询
     *
     * @param current     页码
     * @param size        页长
     * @param subjectName 科目名
     * @return JSON
     */
    @GetMapping("/{current}/{size}/{subjectName}")
    JsonRequest<List<Subject>> selectAllLikeName(@PathVariable Integer current, @PathVariable Integer size, @PathVariable String subjectName) {
        return subjectService.selectAllLikeName(current, size, subjectName);
    }

    /**
     * 根据科目编号查询
     *
     * @param subjectId 科目编号
     * @return JSON
     */
    @GetMapping("/{subjectId}")
    JsonRequest<Subject> selectAllById(@PathVariable Long subjectId) {
        return subjectService.selectAllById(subjectId);
    }

    /**
     * 添加科目
     *
     * @param subject 需提供:科目名
     * @return JSON
     */
    @PostMapping
    JsonRequest<Integer> addSubject(@RequestBody Subject subject) {
        return subjectService.addSubject(subject);
    }

    /**
     * 修改科目
     *
     * @param subject 需提供:科目编号,可提供:科目名
     * @return JSON
     */
    @PutMapping
    JsonRequest<Integer> updateSubjectById(@RequestBody Subject subject) {
        return subjectService.updateSubjectById(subject);
    }

    /**
     * 批量删除或恢复科目
     *
     * @param map <p>
     *            key:科目id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @DeleteMapping
    JsonRequest<Integer> deleteSubjectById(@RequestBody Map<Long, Boolean> map) {
        return subjectService.deleteSubjectById(map);
    }
}

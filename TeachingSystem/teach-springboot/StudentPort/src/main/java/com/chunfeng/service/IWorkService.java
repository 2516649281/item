package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.SubmitWork;

import java.util.List;
import java.util.Map;

/**
 * 学生端业务层接口
 */
public interface IWorkService extends IService<SubmitWork> {

    /**
     * 查询所有学生提交的作业
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    JsonRequest<List<SubmitWork>> selectAllWork(Integer current, Integer size);

    /**
     * 根据学生编号查询提交的作业
     *
     * @param current   页码
     * @param size      页长
     * @param studentId 学生编号
     * @return JSON
     */
    JsonRequest<List<SubmitWork>> selectWorkByStudentWork(Integer current, Integer size, Long studentId);

    /**
     * 提交作业
     *
     * @param submitWork 需提供:学生编号,作业编号,作业内容
     * @return JSON
     */
    JsonRequest<Integer> addWork(SubmitWork submitWork);

    /**
     * 修改已提交的作业
     *
     * @param submitWork 需提供:提交编号,可提供:学生编号,作业编号,作业内容
     * @return JSON
     */
    JsonRequest<Integer> updateWorkById(SubmitWork submitWork);

    /**
     * 批量删除或恢复已提交的作业
     *
     * @param map <p>
     *            key:提交作业id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    JsonRequest<Integer> deleteWorkById(Map<Long, Boolean> map);
}

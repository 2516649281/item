package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.CreateWork;
import com.chunfeng.entity.JsonRequest;

import java.util.List;
import java.util.Map;

/**
 * 教师端业务层接口
 */
public interface IWorkService extends IService<CreateWork> {

    /**
     * 分页查询已经布置的作业
     *
     * @param current 页码
     * @param size    页数
     * @return JSON
     */
    JsonRequest<List<CreateWork>> selectWork(Integer current, Integer size);


    /**
     * 根据教师编号查询作业
     *
     * @param current   页码
     * @param size      每页显示数
     * @param teacherId 教师编号
     * @return JSON
     */
    JsonRequest<List<CreateWork>> selectAllWorkByTeacherId(Integer current, Integer size, Long teacherId);

    /**
     * 添加作业信息
     *
     * @param createWork 需提供:作业名,作业描述(可以是文件),班级编号
     * @return JSON
     */
    JsonRequest<Integer> addWork(CreateWork createWork);

    /**
     * 修改作业信息
     *
     * @param createWork 需提供:作业编号,可提供:作业名,作业描述(可以是文件),班级编号
     * @return JSON
     */
    JsonRequest<Integer> updateWorkById(CreateWork createWork);

    /**
     * 批量删除或恢复已提交的作业
     *
     * @param map <p>
     *            key:提交作业id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    JsonRequest<Integer> deleteWork(Map<Long, Boolean> map);
}

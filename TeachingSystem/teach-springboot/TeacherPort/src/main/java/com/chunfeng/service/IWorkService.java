package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.CreateWork;
import com.chunfeng.util.JsonRequest;

import java.util.List;

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
     * 删除作业信息
     *
     * @param workId 作业编号
     * @param index  操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    JsonRequest<Integer> deleteWork(Long workId, Boolean index);

}

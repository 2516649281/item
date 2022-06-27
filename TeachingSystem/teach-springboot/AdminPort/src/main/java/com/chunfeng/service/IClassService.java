package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.Class;
import com.chunfeng.util.JsonRequest;

import java.util.List;

/**
 * 班级管理业务层接口
 */
public interface IClassService extends IService<Class> {

    /**
     * 查询所有班级
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    JsonRequest<List<Class>> selectAllClass(Integer current, Integer size);

    /**
     * 根据班级名查询所有班级
     *
     * @param current   页码
     * @param size      页长
     * @param className 班级名称
     * @return JSON
     */
    JsonRequest<List<Class>> selectAllClassLikeName(Integer current, Integer size, String className);

    /**
     * 根据班级编号查询班级
     *
     * @param classId 班级编号
     * @return JSON
     */
    JsonRequest<Class> selectAllClassById(Long classId);

    /**
     * 添加班级
     *
     * @param aClass 需提供:班级名称
     * @return JSON
     */
    JsonRequest<Integer> addClass(Class aClass);

    /**
     * 修改班级
     *
     * @param aClass 需提供:班级编号,可提供:班级名称
     * @return JSON
     */
    JsonRequest<Integer> updateClassById(Class aClass);

    /**
     * 删除或恢复班级
     *
     * @param classId 班级编号
     * @param index   操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    JsonRequest<Integer> deleteClassById(Long classId, Boolean index);
}

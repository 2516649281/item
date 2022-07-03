package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.Subject;

import java.util.List;
import java.util.Map;

/**
 * 科目业务层接口
 */
public interface ISubjectService extends IService<Subject> {

    /**
     * 查询所有科目
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    JsonRequest<List<Subject>> selectAllSubject(Integer current, Integer size);

    /**
     * 根据科目名查询
     *
     * @param current     页码
     * @param size        页长
     * @param subjectName 科目名
     * @return JSON
     */
    JsonRequest<List<Subject>> selectAllLikeName(Integer current, Integer size, String subjectName);

    /**
     * 根据科目编号查询
     *
     * @param subjectId 科目编号
     * @return JSON
     */
    JsonRequest<Subject> selectAllById(Long subjectId);

    /**
     * 添加科目
     *
     * @param subject 需提供:科目名
     * @return JSON
     */
    JsonRequest<Integer> addSubject(Subject subject);

    /**
     * 修改科目
     *
     * @param subject 需提供:科目编号,可提供:科目名
     * @return JSON
     */
    JsonRequest<Integer> updateSubjectById(Subject subject);

    /**
     * 批量删除或恢复科目
     *
     * @param map <p>
     *            key:科目id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    JsonRequest<Integer> deleteSubjectById(Map<Long, Boolean> map);
}

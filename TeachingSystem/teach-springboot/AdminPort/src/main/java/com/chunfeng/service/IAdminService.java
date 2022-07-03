package com.chunfeng.service;

import com.chunfeng.entity.Admin;
import com.chunfeng.entity.JsonRequest;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;
import java.util.Map;

/**
 * 管理员业务层接口
 */
public interface IAdminService {
    /**
     * 查询所有管理员
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    JsonRequest<List<Admin>> selectAllAdmin(Integer current, Integer size);

    /**
     * 按照住址查询管理员
     *
     * @param current      页码
     * @param size         页长
     * @param adminAddress 住址
     * @return JSON
     */
    JsonRequest<List<Admin>> selectAllAdminByAddress(Integer current, Integer size, String adminAddress);

    /**
     * 根据编号查询管理员
     *
     * @param adminId 管理员编号
     * @return JSON
     */
    JsonRequest<Admin> selectAllAdminById(Long adminId);

    /**
     * 添加管理员
     *
     * @param admin 需提供:管理员姓名,管理员年龄,管理员性别,管理员住址,管理员电话,管理员邮箱,班级编号
     * @return JSON
     */
    JsonRequest<Integer> addAdmin(Admin admin);

    /**
     * 修改管理员
     *
     * @param admin 需提供:管理员编号,可提供:管理员姓名,管理员年龄,管理员性别,管理员住址,管理员电话,管理员邮箱,班级编号
     * @return JSON
     */
    JsonRequest<Integer> updateAdminById(Admin admin);

    /**
     * 批量删除或恢复管理员
     *
     * @param map <p>
     *            key:管理员id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    JsonRequest<Integer> deleteAdminById(Map<Long, Boolean> map);
}

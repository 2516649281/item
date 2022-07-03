package com.chunfeng.controller;

import com.chunfeng.entity.Admin;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理员管理控制层
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends ServiceController {

    /**
     * 管理员业务层
     */
    @Autowired(required = false)
    private IAdminService AdminService;

    /**
     * 查询所有管理员
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @GetMapping("/{current}/{size}")
    JsonRequest<List<Admin>> selectAllAdmin(@PathVariable Integer current, @PathVariable Integer size) {
        return AdminService.selectAllAdmin(current, size);
    }

    /**
     * 按照地址查询管理员
     *
     * @param current 页码
     * @param size    页长
     * @param address 地址
     * @return JSON
     */
    @GetMapping("/{current}/{size}/{address}")
    JsonRequest<List<Admin>> selectAllAdminByAddress(@PathVariable Integer current, @PathVariable Integer size, @PathVariable String address) {
        return AdminService.selectAllAdminByAddress(current, size, address);
    }

    /**
     * 根据编号查询管理员
     *
     * @param AdminId 管理员编号
     * @return JSON
     */
    @GetMapping("/{AdminId}")
    JsonRequest<Admin> selectAllAdminById(@PathVariable Long AdminId) {
        return AdminService.selectAllAdminById(AdminId);
    }

    /**
     * 添加或批量添加管理员
     *
     * @param Admin 需提供:管理员姓名,管理员年龄,管理员性别,管理员住址,管理员电话,管理员邮箱,地址
     * @return JSON
     */
    @PostMapping
    JsonRequest<Integer> addAdmin(@RequestBody Admin Admin) {
        return AdminService.addAdmin(Admin);
    }

    /**
     * 修改管理员
     *
     * @param Admin 需提供:管理员编号,可提供:管理员姓名,管理员年龄,管理员性别,管理员住址,管理员电话,管理员邮箱,地址
     * @return JSON
     */
    @PutMapping
    JsonRequest<Integer> updateAdminById(@RequestBody Admin Admin) {
        return AdminService.updateAdminById(Admin);
    }

    /**
     * 批量删除或恢复管理员
     *
     * @param map <p>
     *            key:管理员id
     *            <p>
     *            value:操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @DeleteMapping
    JsonRequest<Integer> deleteAdminById(@RequestBody Map<Long, Boolean> map) {
        return AdminService.deleteAdminById(map);
    }
}

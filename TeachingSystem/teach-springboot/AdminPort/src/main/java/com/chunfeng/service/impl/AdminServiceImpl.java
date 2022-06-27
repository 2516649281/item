package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.AdminMapper;
import com.chunfeng.dao.LogMapper;
import com.chunfeng.entity.Admin;
import com.chunfeng.entity.Log;
import com.chunfeng.service.IAdminService;
import com.chunfeng.service.ex.addException.AddException;
import com.chunfeng.service.ex.deleteExcpption.DeleteException;
import com.chunfeng.service.ex.logException.LogAddErrorException;
import com.chunfeng.service.ex.logException.LogSelectErrorException;
import com.chunfeng.service.ex.logException.LogUpdateErrorException;
import com.chunfeng.service.ex.selectException.SelectSourceIsNullException;
import com.chunfeng.service.ex.updateException.UpdateException;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 管理员管理业务层实现类
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    /**
     * 管理员持久层
     */
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 日志持久层
     */
    @Autowired
    private LogMapper logMapper;


    /**
     * 时间格式
     */
    @Value("${date.format}")
    private String dateFormat;

    /**
     * 查询所有管理员
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @Cacheable(value = "admin_page", key = "#current")
    @Override
    public JsonRequest<List<Admin>> selectAllAdmin(Integer current, Integer size) {
        Page<Admin> adminPage = adminMapper.selectPage(new Page<>(current, size), null);//获取所有管理员
        if (adminPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        List<Admin> adminList = adminPage.getRecords();//获取所有管理员
        for (Admin admin : adminList) {
            Log log = logMapper.selectById(admin.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            admin.setLog(log);//添加
        }
        long pageSize = adminPage.getTotal();
        return new JsonRequest<>(200, "", adminList, pageSize);
    }

    /**
     * 按照住址编号查询管理员
     *
     * @param current      页码
     * @param size         页长
     * @param adminAddress 住址
     * @return JSON
     */
    @Cacheable(value = "admin_address", key = "#adminAddress+'_'+#current")
    @Override
    public JsonRequest<List<Admin>> selectAllAdminByAddress(Integer current, Integer size, String adminAddress) {
        Page<Admin> adminPage = adminMapper.selectPage(
                new Page<>(current, size),
                new LambdaQueryWrapper<Admin>()
                        .like(Admin::getAdminAddress, adminAddress));//获取所有管理员
        if (adminPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        List<Admin> adminList = adminPage.getRecords();//获取所有管理员
        for (Admin admin : adminList) {
            Log log = logMapper.selectById(admin.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            admin.setLog(log);//添加
        }
        return new JsonRequest<>(200, "", adminList, adminPage.getTotal());
    }

    /**
     * 根据编号查询管理员
     *
     * @param adminId 管理员编号
     * @return JSON
     */
    @Override
    public JsonRequest<Admin> selectAllAdminById(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        return new JsonRequest<>(200, "", admin, null);
    }

    /**
     * 添加管理员
     *
     * @param admin 需提供:管理员姓名,管理员年龄,管理员性别,管理员住址,管理员电话,管理员邮箱
     * @return JSON
     */
    @CacheEvict(value = {"admin_address", "admin_page"}, allEntries = true)
    @Override
    public JsonRequest<Integer> addAdmin(Admin admin) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        int logColumn = logMapper.insert(log);//添加日志
        if (logColumn < 1) {
            throw new LogAddErrorException("拉取日志失败!");
        }
        admin.setLogId(log.getLogId());//获取已添加的日志id
        int column = adminMapper.insert(admin);
        if (column < 1) {
            throw new AddException("添加数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 修改管理员
     *
     * @param admin 需提供:管理员编号,可提供:管理员姓名,管理员年龄,管理员性别,管理员住址,管理员电话,管理员邮箱
     * @return JSON
     */
    @CacheEvict(value = {"admin_address", "admin_page"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateAdminById(Admin admin) {
        Admin adminSource = adminMapper.selectById(admin.getAdminId());//判断管理员信息是否存在
        if (adminSource == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        Log log = new Log(adminSource.getLogId(),
                new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logMapper.updateById(log);//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        int column = adminMapper.updateById(admin);
        if (column < 1) {
            throw new UpdateException("修改数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 删除或恢复管理员
     *
     * @param adminId 管理员编号
     * @param index   操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @CacheEvict(value = {"admin_address", "admin_page"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteAdminById(Long adminId, Boolean index) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        int column = logMapper.updateById(
                new Log(admin.getLogId(), index ? 1 : 0,
                        new SimpleDateFormat(dateFormat).format(new Date())));
        if (column < 1) {
            throw new DeleteException("删除失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }
}

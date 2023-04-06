package com.chunfeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户管理公共业务层接口
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param user 用户信息:账号,密码
     * @return JSON
     */
    JsonRequest<String> login(User user);

    /**
     * 用户注册
     *
     * @param user 用户信息:账号,密码
     * @return JSON
     */
    JsonRequest<Integer> register(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息:编号,账号,密码
     * @return JSON
     */
    JsonRequest<Integer> updateUser(User user);

    /**
     * 注销
     *
     * @param userId 用户编号
     * @return JSON
     */
    JsonRequest<Integer> deleteUser(Long userId);


    /**
     * 根据用户编号上传头像
     *
     * @param file   头像文件
     * @param userId 用户编号
     * @return JSON
     */
    JsonRequest<Integer> headerUpload(MultipartFile file, Long userId);

    /**
     * 绑定身份信息
     *
     * @param userId    用户编号
     * @param userIndex 身份编号
     * @return JSON
     */
    JsonRequest<Integer> BindIdentity(Long userId, Long userIndex);
}

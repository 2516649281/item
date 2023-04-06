package com.chunfeng.controller;

import com.chunfeng.entity.JsonRequest;
import com.chunfeng.entity.User;
import com.chunfeng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户管理控制层
 */
@RestController
@RequestMapping("/user")
public class UserController extends ServiceController {

    /**
     * 用户业务层
     */
    @Autowired(required = false)
    private IUserService userService;

    /**
     * 用户登录
     *
     * @param user 用户信息:账号,密码
     * @return JSON
     */
    @PostMapping("/login")
    JsonRequest<String> login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 用户注册
     *
     * @param user 用户信息:账号,密码
     * @return JSON
     */
    @PostMapping("/register")
    JsonRequest<Integer> register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息:编号,账号,密码
     * @return JSON
     */
    @PutMapping
    JsonRequest<Integer> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 注销
     *
     * @param user 用户信息:编号
     * @return JSON
     */
    @DeleteMapping
    JsonRequest<Integer> deleteUser(@RequestBody User user) {
        return userService.deleteUser(user.getUserId());
    }

    /**
     * 根据用户编号上传头像
     *
     * @param file   头像文件
     * @param userId 用户编号
     * @return JSON
     */
    @PutMapping("/{userId}")
    JsonRequest<Integer> headerUpload(@RequestParam("avatar") MultipartFile file, @PathVariable Long userId) {
        return userService.headerUpload(file, userId);
    }

    /**
     * 绑定身份信息
     *
     * @param userId    用户编号
     * @param userIndex 身份编号
     * @return JSON
     */
    @PutMapping("/{userId}/{userIndex}")
    JsonRequest<Integer> bindIdentity(@PathVariable Long userId, @PathVariable Long userIndex) {
        return userService.BindIdentity(userId, userIndex);
    }

    /**
     * 前端每隔3min向后端发送一次http请求，验证token是否已过期或服务器是否关闭
     *
     * @return JSON
     */
    @GetMapping
    JsonRequest<Boolean> tokenTimer() {
        return new JsonRequest<>(true, null);
    }
}

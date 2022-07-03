package com.chunfeng.Session;

import com.chunfeng.dao.StudentMapper;
import com.chunfeng.entity.User;
import com.chunfeng.service.IRedisService;
import com.chunfeng.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest
public class SessionTest {

    @Autowired(required = false)
    private UserServiceImpl userService;

    @Autowired(required = false)
    private HttpServletRequest httpServletRequest;

    @Autowired(required = false)
    private StudentMapper studentMapper;


    @Autowired(required = false)
    private IRedisService<User> redisService;


    @Test
    void test1() {
        System.out.println(httpServletRequest.getSession().getAttribute("user"));
    }

    @Test
    void test2() {
        Object user = redisService.get("user");
        System.out.println(user);
    }
}

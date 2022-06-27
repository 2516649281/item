package com.chunfeng;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chunfeng.dao.StudentMapper;
import com.chunfeng.entity.User;
import com.chunfeng.service.IRedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentTest {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private IRedisService<User> iRedisService;

    @Test
    void test1() {
        System.out.println(studentMapper.selectPage(new Page<>(1, 1), null).getRecords());
    }

    @Test
    void testRedis() {
        Object user = iRedisService.get("token");
        User user1 = JSON.parseObject(user.toString(), User.class);
        System.out.println(user1);
    }
}

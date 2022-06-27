package com.chunfeng;

import com.chunfeng.entity.User;
import com.chunfeng.service.IRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileServerApplicationTests {

    @Autowired
    private IRedisService<User> redisService;

    @Test
    public void test1() {
        System.out.println(redisService.get("user"));
    }
}

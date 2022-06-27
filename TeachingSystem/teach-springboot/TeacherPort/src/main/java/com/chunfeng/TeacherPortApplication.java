package com.chunfeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.chunfeng.dao.**")
@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class TeacherPortApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherPortApplication.class, args);
    }
}

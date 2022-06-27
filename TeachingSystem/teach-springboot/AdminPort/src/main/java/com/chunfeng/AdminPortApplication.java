package com.chunfeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.chunfeng.dao.**")
@EnableFeignClients
@EnableCaching
public class AdminPortApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminPortApplication.class, args);
    }
}

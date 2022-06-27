package com.chunfeng.util;

import com.chunfeng.config.WebMvcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * 学生端跨域处理
 */
@Configuration
public class CorsUtils extends WebMvcConfig {

    /**
     * 跨域处理
     *
     * @param registry 跨域处理器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
    }
}

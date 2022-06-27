package com.chunfeng.config;

import com.chunfeng.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Spring配置中心
 */
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 登录拦截器
     */
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry 拦截器注册对象
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("拦截器准备就绪");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//黑名单
                .excludePathPatterns(
                        "/user/login",//登录
                        "/user/register",//注册
                        "/file/select/**",//单文件查看
                        "/file/**",//文件上传
                        "/error/**"//错误请求
                );//白名单
    }

    /**
     * 跨域处理
     *
     * @param registry 跨域处理器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("跨域处理器准备就绪");
        //项目中的所有接口都支持跨域
        registry.addMapping("/**")
                //所有地址都可以访问
                .allowedOrigins("*")
                //允许的请求方式
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                //是否支持跨域Cookie
                .allowCredentials(true)
                //允许的请求头
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}

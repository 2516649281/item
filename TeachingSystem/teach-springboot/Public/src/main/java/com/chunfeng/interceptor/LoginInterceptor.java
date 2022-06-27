package com.chunfeng.interceptor;

import com.alibaba.fastjson.JSON;
import com.chunfeng.entity.User;
import com.chunfeng.service.IRedisService;
import com.chunfeng.service.ex.tokenException.TokenIsNullException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor, RequestInterceptor {

    /**
     * redis业务层
     */
    @Autowired
    private IRedisService<User> redisService;

    /**
     * 第二次放行token
     */
    private String token;

    /**
     * 主拦截器
     *
     * @param request  请求
     * @param response 响应
     * @param handler  头信息
     * @return 是否放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //放行第一次请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("token");//获取请求头的token
        this.token = JSON.parseObject(redisService.get("user"), User.class).getToken();
        if (this.token == null || token == null) {//判断token和redis是否为空
            throw new TokenIsNullException("token与redis数据不得为空!");
        }
        log.info("拦截器状态:" + (this.token.equals(token) ? "放行" : "拦截"));
        return this.token.equals(token);//比较请求头的token与redis中存储的token是否一致
    }

    /**
     * feign拦截器:解决远程调用时请求头丢失问题
     *
     * @param template RequestTemplate
     */
    @Override
    public void apply(RequestTemplate template) {
        this.token = JSON.parseObject(redisService.get("user"), User.class).getToken();
        template.header("token", this.token);
    }
}

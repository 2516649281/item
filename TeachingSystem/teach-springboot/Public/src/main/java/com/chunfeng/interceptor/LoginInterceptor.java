package com.chunfeng.interceptor;

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
    @Autowired(required = false)
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
        token = request.getHeader("token");//获取请求头的token
        //由于redis对string类型值的特殊处理(会自动加上双引号)，这里简化开发，直接分割字符串剔除双引号
        String user1 = redisService.get("user").split("\"")[1];//获取redis中的token
        if (token == null || user1 == null) {//判断token和redis是否为空
            throw new TokenIsNullException("token与redis数据不得为空!");
        }
        log.info("拦截器状态:" + (token.equals(user1) ? "放行" : "拦截"));
        return token.equals(user1);//比较请求头的token与redis中存储的token是否一致
    }

    /**
     * feign拦截器:解决远程调用时请求头丢失问题
     *
     * @param template RequestTemplate
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header("token", token);
    }
}

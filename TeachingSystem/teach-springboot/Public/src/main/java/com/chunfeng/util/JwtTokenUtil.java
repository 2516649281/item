package com.chunfeng.util;

import com.chunfeng.service.ex.tokenException.TokenIsNullException;
import com.chunfeng.service.ex.tokenException.TokenVerifyErrorException;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * jwt工具类
 */
public class JwtTokenUtil<T> {

    /**
     * 过期时间
     * 设置15min过期
     */
    private final long time = 1000 * 60 * 60 * 2;

    /**
     * 密钥
     */
    private final String signature = "chunfeng@2516649281$";

    /**
     * 创建token的方法
     *
     * @param t 任意类型
     * @return token
     */
    public String createToken(T t) {
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("user", t)
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
    }

    /**
     * 校验token，布尔类型
     *
     * @param token token
     * @return object
     */
    public Object checkToken(String token) {
        if (token.equals("")) {
            throw new TokenIsNullException("发生了未知异常!");
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        if (claimsJws == null) {
            throw new TokenVerifyErrorException("发生了未知异常!");
        }
        return claimsJws.getBody().get("user");
    }
}
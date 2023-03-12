package com.hardyz.emos.wx.config.shiro;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hardyz.emos.wx.exception.EmosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    @Value("${emos.jwt.password}")
    private String password;

    @Value("@{emos.jwt.expire}")
    private int expire;

    public String createToken(int userId) {
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, expire);
        Algorithm algorithm = Algorithm.HMAC256(password);
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        return token;
    }

    public int getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asInt();
        } catch (Exception e) {
            throw new EmosException("令牌无效。。。");
        }
    }

    public void verifierToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(token);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        jwtVerifier.verify(token);
    }

}

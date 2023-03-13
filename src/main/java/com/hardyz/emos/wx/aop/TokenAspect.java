package com.hardyz.emos.wx.aop;

import com.hardyz.emos.wx.common.util.R;
import com.hardyz.emos.wx.config.shiro.ThreadLocalToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TokenAspect {
    @Autowired
    ThreadLocalToken threadLocalToken;

    @Pointcut("execution(public * com.hardyz.emos.wx.controller.*.*(..))")
    public void aspect() {

    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        R r = (R)point.proceed();
        String token = threadLocalToken.getToken();
        // 如果ThreadLocalToken里面有token的值，说明是更新的token
        if (token != null) {
            r.put("token", token);
            threadLocalToken.clear();
        }
        return r;
    }
}

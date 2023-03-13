package com.hardyz.emos.wx.config.shiro;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    // 用于封装Realm对象
    @Bean("secutiryManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }
    // 用于封装Filter对象
    // 设置Filter拦截路径
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, OAuth2Filter oAuth2Filter) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("oauth2", oAuth2Filter);
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/webjars/**", "anno");
        filterMap.put("/druid/**", "anno");
        filterMap.put("/app/**", "anno");
        filterMap.put("/sys/login", "anno");
        filterMap.put("/swagger/**", "anno");
        filterMap.put("/v2/api-docs", "anno");
        filterMap.put("/swagger-ui.html", "anno");
        filterMap.put("/swagger-resources/**", "anno");
        filterMap.put("/captcha.jpg", "anno");
        filterMap.put("/user/register", "anno");
        filterMap.put("/user/login", "anno");
        filterMap.put("/test/**", "anno");
        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }
    // 管理shiro生命周期
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    // AOP切面类
    // Web方法执行前，验证权限
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}

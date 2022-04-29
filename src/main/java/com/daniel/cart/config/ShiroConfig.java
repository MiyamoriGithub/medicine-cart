package com.daniel.cart.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    // Todo 继续添加权限map，完善权限管理
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean filterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(manager);

        // 将自定义过滤器注册到Shiro中使用
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("authc", new MyFormAuthenticationFilter());
//        filterMap.put("custom", new ShiroUserFilter());
        filterFactoryBean.setFilters(filterMap);

        // 将组件权限的 map 注册到 Shiro 中管理，其他 filterChain 用到的时候再去查
        Map<String, String> authMap = new LinkedHashMap<>();
//        authMap.put("/login", "anon");
//        authMap.put("/employee/*", "anon");
//        authMap.put("/cart/*", "anon");
        authMap.put("/**", "anon");
        filterFactoryBean.setFilterChainDefinitionMap(authMap);
        return filterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager manager(@Qualifier("realm") GlobalRealm globalRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(globalRealm);
        manager.setSessionManager(sessionManager());
        return manager;
    }

    @Bean
    public GlobalRealm realm() {
        GlobalRealm realm = new GlobalRealm();
        // 设置realm使用md5
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    // 自定义的 shiro session 缓存管理器，用于跨域等情况下使用 token 进行验证，不依赖于sessionId
    @Bean
    public SessionManager sessionManager(){
        //将我们继承后重写的shiro session 注册
        ShiroSession shiroSession = new ShiroSession();
        //如果后续考虑多tomcat部署应用，可以使用shiro-redis开源插件来做session 的控制，或者nginx 的负载均衡
        shiroSession.setSessionDAO(new EnterpriseCacheSessionDAO());
        return shiroSession;
    }

    @Bean
    public FilterRegistrationBean replaceTokenFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter( new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("CorsFilter");
        registration.setOrder(1);
        return registration;
    }
}
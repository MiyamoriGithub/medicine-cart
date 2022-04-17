package com.daniel.cart.config;

import com.daniel.cart.mapper.EmployeeMapper;
import com.daniel.cart.service.EmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GlobalRealmTest {

    @Autowired
    EmployeeService service;

    @Test
    public void doGetAuthorizationInfo() {
    }

    @Test
    public void doGetAuthenticationInfo() {
        DefaultSecurityManager manager = new DefaultSecurityManager();
        GlobalRealm realm = new GlobalRealm();
        realm.setService(service);
        SecurityUtils.setSecurityManager(manager);
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        realm.setCredentialsMatcher(matcher);

        manager.setRealm(realm);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("15793579346", "123456");

        try {
            subject.login(token);
            System.out.println("认证是否成功： " + subject.isAuthenticated());
            System.out.println("是否具有权限： " + subject.hasRole("admin"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
package com.daniel.cart.config;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.exception.AuthorizationException;
import com.daniel.cart.mapper.EmployeeMapper;
import com.daniel.cart.service.EmployeeService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// Todo 添加各种异常信息的判断和抛出

@Resource
@Component
public class GlobalRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private EmployeeService service;

    private Logger logger = LoggerFactory.getLogger(GlobalRealm.class);

    public void setService(EmployeeService service) {
        this.service = service;
    }

    // 授权realm
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        Employee employee = service.findByPhone(principal);
        if(employee == null) {
            throw new AuthorizationException(28001, "账户信息不存在或已被禁用");
        }
//        logger.info(employee.getName());
        logger.info(employee.getPhone());
        logger.info(employee.getPassword());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (String role : employee.getRole().getRoleName()) {
            authorizationInfo.addRole(role);
        }

        return authorizationInfo;
    }


    // 认证realm
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String principal = (String) token.getPrincipal();
        logger.info("获取到了token：" + principal);
//        token.setPassword(Md5Utils.code(new String(token.getPassword())).toCharArray());
        Employee employee = service.findByPhone(principal);
        if(employee != null) {
            return new SimpleAuthenticationInfo(employee.getPhone(), employee.getPassword(), this.getName());
        }
        return null;
    }
}

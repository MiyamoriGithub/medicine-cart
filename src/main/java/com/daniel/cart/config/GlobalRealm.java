package com.daniel.cart.config;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.exception.AuthorizationException;
import com.daniel.cart.mapper.EmployeeMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

// Todo 实现全局Realm类
// Todo 添加各种异常信息的判断和抛出

@Resource
public class GlobalRealm extends AuthorizingRealm {

    private EmployeeMapper mapper;

    @Autowired
    public void setMapper(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    // 授权realm
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        Employee employee = mapper.findByPhone(principal);
        if(employee == null) {
            throw new AuthorizationException(28001, "账户信息不存在或已被禁用");
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (String role : employee.getRole().getRoleName()) {
            authorizationInfo.addRole(role);
            System.out.println(role);
        }

        return authorizationInfo;
    }


    // 认证realm
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String principal = (String) token.getPrincipal();
//        token.setPassword(Md5Utils.code(new String(token.getPassword())).toCharArray());
        Employee employee = mapper.findByPhone(principal);
        if(employee != null) {
            return new SimpleAuthenticationInfo(employee.getPhone(), employee.getPassword(), this.getName());
        }
        return null;
    }
}

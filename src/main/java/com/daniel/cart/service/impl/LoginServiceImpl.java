package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.vo.LoginVo;
import com.daniel.cart.exception.LoginException;
import com.daniel.cart.mapper.EmployeeMapper;
import com.daniel.cart.service.LoginService;
import com.daniel.cart.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

    private final EmployeeMapper mapper;

    @Autowired
    public LoginServiceImpl(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean login(LoginVo user) throws LoginException{
        Long id = user.getId();
        String phone = user.getPhone();
        String prePassword = user.getPassword();

        if(id == null && phone == null) {
            throw new LoginException(28001, "无用户名或手机号码");
        }

        if(prePassword == null) {
            throw new LoginException(28008, "无密码");
        }

        String password = Md5Utils.code(user.getPassword());

        Employee employee = null;

        if(null != id) {
            employee = mapper.findById(id);
        }else if(null != phone) {
            employee = mapper.findByPhone(phone);
        }

        if(null == employee) {
            throw new LoginException(28009, "无法找到用户或用户已被禁用");
        }

        if(employee.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}

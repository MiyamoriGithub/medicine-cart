package com.daniel.cart.service;

import com.daniel.cart.domain.vo.LoginVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class LoginServiceTest {

    @Autowired
    LoginService service;

    @Test
    public void login() {
        LoginVo user = new LoginVo();
//        user.setId(1l);
        user.setPhone("13312333567");
        user.setPassword("123456");
        System.out.println(service.login(user));
    }
}
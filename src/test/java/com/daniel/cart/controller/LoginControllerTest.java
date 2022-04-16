package com.daniel.cart.controller;

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
public class LoginControllerTest {

    @Autowired
    LoginController controller;

    @Test
    public void loginTest() {
        LoginVo user = new LoginVo();
        user.setId((long)1);
        user.setPassword("123456");
    }
}

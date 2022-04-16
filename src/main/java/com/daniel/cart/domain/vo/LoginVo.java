package com.daniel.cart.domain.vo;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class LoginVo {
    private Long id;
    private String phone;
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

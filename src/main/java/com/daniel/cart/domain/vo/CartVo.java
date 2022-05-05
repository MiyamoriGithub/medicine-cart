package com.daniel.cart.domain.vo;

import com.daniel.cart.domain.enums.CartStateEnum;
import lombok.Getter;

@Getter
public class CartVo extends PageVo {
    private Long departmentId;
    private String departmentName;
    private CartStateEnum state;

    public void setDepartmentId(Long departmentId) {
        if(departmentName!=null) {
            return;
        }
        this.departmentId = departmentId;
    }

    public void setDepartmentName(String departmentName) {
        StringBuffer tmp = new StringBuffer();
        tmp.append('%');
        tmp.append(departmentName);
        tmp.append('%');
    }

    public void setState(CartStateEnum state) {
        this.state = state;
    }
}

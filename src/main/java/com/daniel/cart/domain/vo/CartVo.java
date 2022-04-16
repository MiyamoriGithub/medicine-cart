package com.daniel.cart.domain.vo;

import com.daniel.cart.domain.CartStateEnum;

public class CartVo extends PageVo {
    private Long id;
    private Long departmentId;
    private String departmentName;
    private CartStateEnum state;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDepartmentId(Long departmentId) {
        if(departmentName!=null) {
            return;
        }
        this.departmentId = departmentId;
    }

    public void setDepartmentName(String departmentName) {
        if(id != null) {
            return;
        }
        StringBuffer tmp = new StringBuffer();
        tmp.append('%');
        tmp.append(departmentName);
        tmp.append('%');
    }

    public void setState(CartStateEnum state) {
        this.state = state;
    }
}

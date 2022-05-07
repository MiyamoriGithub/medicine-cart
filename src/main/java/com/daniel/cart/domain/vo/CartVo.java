package com.daniel.cart.domain.vo;

import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.util.AttributeCheck;
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
        if(AttributeCheck.isStringOk(departmentName)) {
            tmp.append(departmentName.trim());
        }
        tmp.append('%');
        departmentName = tmp.toString();
    }

    public void setState(CartStateEnum state) {
        this.state = state;
    }
}

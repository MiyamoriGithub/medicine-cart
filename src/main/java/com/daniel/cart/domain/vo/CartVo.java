package com.daniel.cart.domain.vo;

import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.util.AttributeCheck;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartVo extends PageVo {
    private Long departmentId;
    private String departmentName;
    private CartStateEnum state;

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void setDepartmentName(String departmentName) {
        StringBuffer tmp = new StringBuffer();
        tmp.append('%');
        if(AttributeCheck.isStringOk(departmentName)) {
            tmp.append(departmentName.trim());
        }
        tmp.append('%');
        this.departmentName = tmp.toString();
    }

    public void setState(CartStateEnum state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CartVo{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", state=" + state +
                '}';
    }
}

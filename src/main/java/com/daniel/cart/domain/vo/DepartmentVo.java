package com.daniel.cart.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentVo extends PageVo{

    private String nameCondition;

    public void setNameCondition(String nameCondition) {
        StringBuffer tmp = new StringBuffer();
        tmp.append("%");
        if(nameCondition != null) {
            tmp.append(nameCondition.trim());
        }
        tmp.append("%");
        this.nameCondition = tmp.toString();
    }
}

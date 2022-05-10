package com.daniel.cart.domain.enums;


import com.daniel.cart.util.AttributeCheck;
import lombok.ToString;
import org.apache.commons.lang3.EnumUtils;

@ToString
public enum CartStateEnum {
    free("空闲"),
    inventory("维护中"),
    emergency("急救中"),
    unknown("未知");
    

    private String name;

    private CartStateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Boolean roleCheck(String state) {
        if(AttributeCheck.isStringOk(state)) {
            boolean res = EnumUtils.isValidEnum(CartStateEnum.class, state);
            return res;
        }
        return false;
    }

}

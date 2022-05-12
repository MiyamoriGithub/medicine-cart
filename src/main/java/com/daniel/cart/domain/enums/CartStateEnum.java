package com.daniel.cart.domain.enums;


import com.daniel.cart.util.AttributeCheck;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.EnumUtils;

@Getter
@ToString
public enum CartStateEnum {
    free("空闲", "free"),
    inventory("维护中", "inventory"),
    emergency("急救中", "emergency"),
    unknown("未知", "unknown");

    private final String name;
    private final String valueName;

    CartStateEnum(String name, String valueName) {
        this.name = name;
        this.valueName = valueName;
    }

    public String getName() {
        return name;
    }

    public static Boolean roleCheck(String state) {
        if(AttributeCheck.isStringOk(state)) {
            return EnumUtils.isValidEnum(CartStateEnum.class, state);
        }
        return false;
    }

}

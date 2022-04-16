package com.daniel.cart.domain.enums;


import lombok.ToString;

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

}

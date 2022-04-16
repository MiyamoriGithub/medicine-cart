package com.daniel.cart.domain;

public enum CartStateEnum {
    free("空闲"),
    inventory("维护"),
    emergency("急救"),
    unknown("未知");

    private String name;

    private CartStateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

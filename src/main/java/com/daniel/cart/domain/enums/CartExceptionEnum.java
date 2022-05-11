package com.daniel.cart.domain.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 车辆异常情况枚举
 *
 * @author Daniel Zheng
 * @create 2022-05-11 13:22
 **/

@Getter
public enum CartExceptionEnum {
    expire("药品过期", "expire"),
    temporary("药品临期", "temporary"),
    vacant("药品空缺", "vacant");

    private String name;
    private String roleName;

    private CartExceptionEnum(String name, String roleName){
        this.name = name;
        this.roleName = roleName;
    }
}

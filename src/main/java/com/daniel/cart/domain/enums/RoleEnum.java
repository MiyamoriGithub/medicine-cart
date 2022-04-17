package com.daniel.cart.domain.enums;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public enum RoleEnum {
    admin("admin"),
    manager("manager"),
    common("common");

    private String roleName;

    private RoleEnum(String name) {
        this.roleName = name;
    }

    public List<String> getRoleName() {
        List<String> list = new ArrayList<>();
        // 注意没有使用break语句，较大的权限会添加较小的权限
        switch(roleName) {
            case "admin" :
                list.add(admin.roleName);
            case "manager":
                list.add(manager.roleName);
            case "common":
                list.add(common.roleName);
                break;
        }
        return list;
    }

}

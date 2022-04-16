package com.daniel.cart.domain.enums;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
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

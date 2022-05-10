package com.daniel.cart.domain.enums;

import com.daniel.cart.util.AttributeCheck;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.EnumUtils;

import java.util.ArrayList;
import java.util.List;

// Todo 添加部门内部对应的权限，通过限制方法来完成吧，查询的时候获取到用户的token然后再将部门信息作为参数传入进行查询

@Getter
@ToString
public enum RoleEnum {
    admin("admin", "超级管理员"),
    manager("manager", "部门管理员"),
    common("common", "普通用户");

    private String roleName;
    private String name;

    private RoleEnum(String roleName, String name) {
        this.roleName = roleName;
        this.name = name;
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

    public static Boolean roleCheck(String role) {
        if(AttributeCheck.isStringOk(role)) {
            boolean res = EnumUtils.isValidEnum(RoleEnum.class, role);
            return res;
        }
        return false;
    }

}

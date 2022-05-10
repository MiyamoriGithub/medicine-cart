package com.daniel.cart.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.daniel.cart.domain.enums.RoleEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * 员工实体类
 */

@Getter
@Setter
@Component
@Data
public class Employee {
    private Long id;                    // 员工id employee_id
    private String name;                // 员工姓名 employee_name
    private String phone;               // 员工手机号，employee_phone 作为联系方式，和登录名无关
    private Date addTime;               // 员工信息添加时间 employee_add_time
    private Long departmentId;          // 员工部门id department_id
    private String departmentName;
//    private Boolean isEnable;
    @JSONField(serialize = false)
    private String password;
//    @JSONField(serialize = false)
    private RoleEnum role;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", addTime=" + addTime +
                ", departmentName='" + departmentName + '\'' +
                ", role=" + role.getName() +
                '}';
    }

    @JSONField(name = "auth")
    public String getAuth(){
        return this.role.getName();
    }
}

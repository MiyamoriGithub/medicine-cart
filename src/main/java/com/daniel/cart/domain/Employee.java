package com.daniel.cart.domain;

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
    private Boolean isEnable;
    private String password;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", addTime=" + addTime +
                ", departmentName='" + departmentName + '\'' +
                ", isEnable=" + isEnable +
                '}';
    }
}

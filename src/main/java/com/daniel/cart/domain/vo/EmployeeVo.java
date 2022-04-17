package com.daniel.cart.domain.vo;

import com.daniel.cart.domain.enums.RoleEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 模糊查询 Request 类
 */
@Data
@Component
public class EmployeeVo extends PageVo{
    private String nameCondition;     // 姓名作为模糊查询条件
    private String departmentName;
    private String departmentId;
    private RoleEnum role;

    public void setNameCondition(String nameCondition) {
        this.nameCondition = "%" + nameCondition + "%";
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = "%" + departmentName + "%";
    }

    // Todo 完成其他属性的查询逻辑
}

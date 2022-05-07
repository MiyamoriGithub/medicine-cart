package com.daniel.cart.domain.vo;

import com.daniel.cart.domain.enums.RoleEnum;
import com.daniel.cart.util.AttributeCheck;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 模糊查询 Request 类
 */
@Getter
@Setter
@ToString
@Component
public class EmployeeVo extends PageVo{
    private String nameCondition;     // 姓名作为模糊查询条件
    private String departmentName;
    private Long departmentId;
    private RoleEnum role;

    public void setNameCondition(String nameCondition) {
        if(AttributeCheck.isStringOk(nameCondition)) {
            this.nameCondition = "%" + nameCondition.trim() + "%";
        }
    }

    public void setDepartmentName(String departmentName) {
        if(AttributeCheck.isStringOk(departmentName)) {
            this.departmentName = "%" + departmentName.trim() + "%";
        }
    }
}

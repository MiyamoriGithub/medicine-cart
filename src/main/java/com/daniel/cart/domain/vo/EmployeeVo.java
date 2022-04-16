package com.daniel.cart.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 模糊查询 Request 类
 */
@Data
@Component
public class EmployeeVo extends PageVo{
    private String nameCondition;     // 姓名作为模糊查询条件

    public void setNameCondition(String nameCondition) {
        this.nameCondition = '%' + nameCondition + '%';
    }
}

package com.daniel.cart.domain.vo;

import com.daniel.cart.util.AttributeCheck;
import lombok.Getter;
import lombok.Setter;

/**
 * 药品信息实体类查询条件类
 *
 * @author Daniel Zheng
 **/

@Getter
@Setter
public class DrugInfVo extends PageVo{
    private String nameCondition;

    public void setNameCondition(String nameCondition) {
        if (AttributeCheck.isStringOk(nameCondition)) {
            StringBuffer tmp = new StringBuffer();
            tmp.append('%');
            tmp.append(nameCondition.trim());
            tmp.append('%');
            this.nameCondition = tmp.toString();
        }
    }
}

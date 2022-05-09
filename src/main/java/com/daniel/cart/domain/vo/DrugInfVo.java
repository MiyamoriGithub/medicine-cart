package com.daniel.cart.domain.vo;

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
        StringBuffer tmp = new StringBuffer();
        tmp.append('%');
        if(nameCondition != null) {
            tmp.append(nameCondition.trim());
        }
        tmp.append('%');
        this.nameCondition = tmp.toString();
    }
}

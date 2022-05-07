package com.daniel.cart.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DrugVo extends PageVo{
    private Long drugInfId;
    private String nameCondition;
    private String barcode;


    public void setNameCondition(String nameCondition) {
        StringBuffer buffer = new StringBuffer();
        buffer.append('%');
        buffer.append(nameCondition.trim());
        buffer.append('%');
        this.nameCondition = buffer.toString();
    }
}

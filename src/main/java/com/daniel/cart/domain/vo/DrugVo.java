package com.daniel.cart.domain.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrugVo extends PageVo{
    private String nameCondition;
    private String barcode;


    public void setNameCondition(String nameCondition) {
        this.nameCondition = "%" + nameCondition + "%";
    }
}

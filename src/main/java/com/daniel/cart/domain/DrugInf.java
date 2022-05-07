package com.daniel.cart.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 药品通用信息实体类
 *
 * @author Daniel Zheng
 **/

@Data
@Component
public class DrugInf {
    private Long drugInfId;
    private String barcode;
    private String name;
    private Integer shelfLife;
    private Integer drugPackage;

    public DrugInf() {}
}

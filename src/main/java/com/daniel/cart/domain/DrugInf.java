package com.daniel.cart.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 药品通用信息实体类
 *
 * @author Daniel Zheng
 **/

@Data
@Getter
@Setter
@Component
public class DrugInf {
    private Long drugInfId;
    private String barcode;
    private String name;
    private Integer shelfLife;
    private Integer drugPackage;

    public DrugInf() {}

    public DrugInf(String barcode, String name, Integer shelfLife) {
        this.barcode = barcode;
        this.name = name;
        this.shelfLife = shelfLife;
    }

    @Override
    public String toString() {
        return "DrugInf{" +
                "drugInfId=" + drugInfId +
                ", barcode='" + barcode + '\'' +
                ", name='" + name + '\'' +
                ", shelfLife=" + shelfLife +
                ", drugPackage=" + drugPackage +
                '}';
    }
}

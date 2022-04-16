package com.daniel.cart.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Getter
@Setter
public class Drug {
    private Integer id;         // 药品的数据库id drug_id
    private String barcode;     // 药品的条码 drug_barcode
    private String name;        // 药品名称 drug_name
    private Date productDate;   // 药品生产日期 drug_product_date
    private Integer shelfLife;  // 药品保质期，单位：天，其他单位需转换为天后再存入 drug_shelf_life

    public Drug() {}

    @Override
    public String toString() {
        return "Drug{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", name='" + name + '\'' +
                ", productDate=" + productDate +
                ", shelfLife=" + shelfLife +
                '}';
    }
}

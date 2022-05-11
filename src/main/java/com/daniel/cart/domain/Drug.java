package com.daniel.cart.domain;

import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.exception.DrugOperateException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

// Todo 建立 DrugInf 实体类，通过 Drug 包含 DrugInf 的方式完善相关信息的表示，并完善多表查询的 Sql 语句

@Component
@Getter
@Setter
public class Drug {
    private Long id;         // 药品的数据库id drug_id
    private Timestamp productDate;   // 药品生产日期 drug_product_date
    private Integer stock;      // 药品库存，单位：支
    private Long drugInfId;
    //    private String barcode;     // 药品的条码 drug_barcode
    //    private String name;        // 药品名称 drug_name
    //    private Integer shelfLife;  // 药品保质期，单位：天，其他单位需转换为天后再存入 drug_shelf_life
    //    private Integer pack;       // 药品包装规格，单位：支/盒，在单位转换时使用
    private DrugInf drugInf;

    public Drug() {}


    @Override
    public String toString() {
        return "Drug{" +
                "id=" + id +
                ", productDate=" + productDate +
                ", stock=" + stock +
                ", drugInf=" + drugInf.toString() +
                '}';
    }
}

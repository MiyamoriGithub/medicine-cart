package com.daniel.cart.domain.res;

import com.daniel.cart.domain.Drug;
import lombok.Data;

import java.util.List;

/**
 * 药品信息实体类（封装药品生产日期信息）
 *
 * @author Daniel Zheng
 **/

@Data
public class DrugInfRes {
    private Long drugInfId;
    private String barcode;
    private String name;
    private Integer shelfLife;
    private List<Drug> drugList;
}

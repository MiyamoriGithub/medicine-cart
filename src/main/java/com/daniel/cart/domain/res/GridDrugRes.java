package com.daniel.cart.domain.res;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.enums.DrugExceptionEnum;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 携带药品信息的 Grid 实体类
 *
 * @author Daniel Zheng
 **/

@Data
@Component
public class GridDrugRes {
    private Long id;

    private Boolean isFull;
    private Integer capacity;               // 当前格的容量

    private Long cartId;                    // 所在急救车id，对应cart对象
    private Integer layer;                  // 所在急救车层数
    private Integer row;                    // 所在层的行数
    private Integer column;                 // 所在层的列数

    private Long drugInfId;
    private String drugName = "暂无药品";

    List<Drug> drugs;

    List<String> exceptions;
}

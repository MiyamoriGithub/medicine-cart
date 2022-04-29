package com.daniel.cart.domain.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Grid 实体类的查询限制条件 Vo 对象
 *
 * @author Daniel Zheng
 **/

@Getter
@Setter
public class GridVo extends PageVo{
    private Long drugInfId;
    private Long cartId;
    private Integer layer;

    public GridVo(){}

}

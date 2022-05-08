package com.daniel.cart.domain.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockVo extends PageVo{
    private Long drugId;
    private Long drugInfId;
    private Long gridId;
    private Long cartId;
    private Integer layer;
    private Long departmentId;
}

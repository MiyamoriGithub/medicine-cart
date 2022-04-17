package com.daniel.cart.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Component
public class Block {
    private Long id;
//    private Integer block;
    private Long drugId;
    private String drugName;
}



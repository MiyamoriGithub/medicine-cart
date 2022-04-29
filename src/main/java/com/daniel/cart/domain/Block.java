package com.daniel.cart.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Component
public class Block {
    private Long id;
    private Long drugId;
    private Long drugInfId;
    private Long gridId;
    private String drugName;
}



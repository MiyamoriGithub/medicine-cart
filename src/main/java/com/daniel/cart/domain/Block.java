package com.daniel.cart.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
public class Block {
    private Long id;
    private Long drugId;                // 药品 id
    private Long drugInfId;             // 药品信息 id
    private String drugName;            // 药品名称（方便 debug 加的，实际不应该有多余的属性）
    private Timestamp productDate;
    private Integer shelfLife;
    private Long gridId;                // 对应的 grid id
    private Integer serial;             // 取决于所在Grid的容量，为1-8或者1-10
}



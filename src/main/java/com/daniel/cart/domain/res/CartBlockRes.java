package com.daniel.cart.domain.res;

import com.alibaba.fastjson.annotation.JSONField;
import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.enums.CartStateEnum;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * Cart 和 Block 数据库联查结果
 *
 * @author Daniel Zheng
 **/

@Data
public class CartBlockRes {
    private Long id;                // cart_id bigint primary key
    private Long departmentId;   // department_id int
    private String departmentName;
    private Date addTime;           // add_time timestamp not null
    @JSONField(serialize = false)
    private CartStateEnum state;           // status_type enum('free','inventory','emergency','unknown')
    List<Block> blockList;

    @JSONField(name = "cartState")
    public String getCartState() {
        return this.state.getName();
    }

    public Cart getCart() {
        return new Cart(id, departmentId, departmentName, addTime, state);
    }
}

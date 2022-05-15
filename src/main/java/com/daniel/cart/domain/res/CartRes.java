package com.daniel.cart.domain.res;

import com.daniel.cart.domain.Cart;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 急救车状态查询的结果实体类
 *
 * @author Daniel Zheng
 **/

@Data
@Component
public class CartRes {
    // 急救车实体类
    Cart cart;
    // 异常名称列表
    List<String> exceptions;

    public CartRes() {
    }

    public CartRes(Cart cart, List<String> exceptions) {
        this.cart = cart;
        this.exceptions = exceptions;
    }
}

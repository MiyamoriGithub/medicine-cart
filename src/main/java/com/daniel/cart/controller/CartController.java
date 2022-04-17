package com.daniel.cart.controller;


import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Todo 完成剩余的接口
// Todo 增加账户权限检查逻辑

@Api(value = "急救车Controller", tags = {"急救车信息管理接口"})
@RestController
@CrossOrigin
@RequestMapping("cart")
public class CartController {

    private final CartService service;
    private Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @ApiOperation("查询全部急救车信息")
    @GetMapping("findAll")
    public Result findAll() {
        List<Cart> carts = service.findAll();
        return Result.ok().data("items", carts);
    }

    @ApiOperation("根据id查询急救车信息")
    @GetMapping("findById")
    public Result findById(Long id) {
        Cart cart = service.findById(id);
        return Result.ok().data("item", cart);
    }
}

package com.daniel.cart.controller;


import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 急救车管理接口
 *
 * @author Daniel Zheng
 **/

// Todo 增加账户权限检查逻辑

@Api(value = "急救车Controller", tags = {"急救车管理接口"})
@RestController
@CrossOrigin
@RequestMapping("cart")
public class CartController implements AbstractController {

    private final CartService service;
    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @ApiOperation("查询急救车信息")
    @GetMapping("find")
    @Override
    public Result find(
            @RequestParam(value = "抢救车id信息", required = false) Long id,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        if(id != null) {
            Cart cart = service.findById(id);
            return Result.ok().data("item", cart);
        } else if(start != null && pageSize != null) {
            List<Cart> carts = service.findAll(start, pageSize);
            Long count = service.getCount();
            return Result.ok().data("items", carts).data("count", count);
        }else {
            List<Cart> carts = service.findAll();
            Long count = service.getCount();
            return Result.ok().data("items", carts).data("count", count);
        }
    }

    @ApiOperation("查询急救车的全部状态枚举值")
    @GetMapping("getStates")
    public Result getStates() {
        Map<String, String> states = service.getAllStates();
        return Result.ok().data("items", states);
    }

    @ApiOperation("根据限制条件查询急救车信息以及信息条目数量")
    @GetMapping("departAndState")
    public Result departAndState(
            @RequestParam(value = "部门id信息") Long departmentId,
            @RequestParam(value = "状态信息") String state,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量", required = false) Integer pageSize
    ) {
        List<Cart> carts;
        if(start == null || pageSize == null) {
            carts = service.findByLimit(departmentId, state);
        } else {
            carts = service.findByLimit(departmentId, state, start, pageSize);
        }
        Long count = service.getCountByDepartment(departmentId);
        Result res = Result.ok().data("items", carts);
        return res.data("count", count);
    }
    @ApiOperation("根据部门查询急救车信息以及信息条目数量")
    @GetMapping("depart")
    public Result depart(
            @RequestParam("部门id信息") Long departmentId,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量", required = false) Integer pageSize
    ) {
        List<Cart> carts;
        if(start == null || pageSize == null) {
            carts = service.findAllByDepartment(departmentId);
        } else {
            carts = service.findAllByDepartment(departmentId, start, pageSize);
        }
        Long count = service.getCountByDepartment(departmentId);
        Result res = Result.ok().data("items", carts);
        return res.data("count", count);
    }

    @ApiOperation("根据状态查询急救车信息以及信息条目数量")
    @GetMapping("state")
    public Result state(
            @RequestParam("状态信息") String state,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量", required = false) Integer pageSize
    ) {
        List<Cart> carts;
        if(start == null || pageSize == null) {
            carts = service.findAllByState(state);
        } else {
            carts = service.findAllByState(state, start, pageSize);
        }
        Long count = service.getCountByState(state);
        Result res = Result.ok().data("items", carts);
        return res.data("count", count);
    }

    @ApiOperation("设置急救车为空闲状态")
    @GetMapping("free")
    public Result setFree(@RequestParam("抢救车id") Long id) {
        Boolean res = service.setCartFree(id);
        return returnMessage(res);
    }

    @ApiOperation("设置急救车为补充药品状态")
    @GetMapping("inventory")
    public Result setInventory(@RequestParam("抢救车id") Long id) {
        Boolean res = service.setCartInventory(id);
        return returnMessage(res);
    }

    @ApiOperation("设置急救车为急救状态")
    @GetMapping("emergency")
    public Result setEmergency(@RequestParam("抢救车id") Long id) {
        Boolean res = service.setCartEmergency(id);
        return returnMessage(res);
    }

    @ApiOperation("添加急救车")
    @GetMapping("add")
    public Result add(@RequestParam("部门id") Long departmentId) {
        Cart cart = new Cart(departmentId);
        Boolean res = service.addCart(cart);
        return returnMessage(res);
    }

    @ApiOperation("删除急救车")
    @GetMapping("remove")
    public Result remove(@RequestParam("急救车id") Long id) {
        Boolean res = service.removeCart(id);
        return returnMessage(res);
    }

    @ApiOperation("修改急救车")
    @GetMapping("modify")
    public Result modify(
            @RequestParam("急救车属性") Long id,
            @RequestParam("急救车部门id") Long departmentId,
            @RequestParam(value = "急救车状态", required = false) String state
            ) {
        Cart cart = service.findById(id);
        cart.setDepartmentId(departmentId);
        if(state != null && EnumUtils.isValidEnum(CartStateEnum.class, state)) {
            cart.setState(CartStateEnum.valueOf(state));
        }
        Boolean res = service.modifyCart(cart);
        return returnMessage(res);
    }

    private Result returnMessage(Boolean res) {
        if(res) {
            return Result.ok().message("操作成功");
        } else {
            return Result.error().message("操作失败");
        }
    }

}

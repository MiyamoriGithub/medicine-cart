package com.daniel.cart.controller;


import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.enums.CartExceptionEnum;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.res.CartRes;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.BlockService;
import com.daniel.cart.service.CartService;
import com.daniel.cart.service.DrugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
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
    private final DrugService drugService;
    private final BlockService blockService;
    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartController(CartService service, DrugService drugService, BlockService blockService) {
        this.service = service;
        this.drugService = drugService;
        this.blockService = blockService;
    }

    @ApiOperation("查询急救车信息")
    @GetMapping("find")
    @Override
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "抢救车id信息") Long id,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
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
            @RequestParam @ApiParam(value = "部门id信息",required = true) Long departmentId,
            @RequestParam @ApiParam(value = "状态信息", required = true) String state,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
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
            @RequestParam @ApiParam(value = "部门id信息",required = true) Long departmentId,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量", required = false) Integer pageSize
    ) {
        List<Cart> carts;
        if(start == null || pageSize == null) {
            carts = service.findByDepartment(departmentId);
        } else {
            carts = service.findByDepartment(departmentId, start, pageSize);
        }
        Long count = service.getCountByDepartment(departmentId);
        Result res = Result.ok().data("items", carts);
        return res.data("count", count);
    }

    @ApiOperation("根据状态查询急救车信息以及信息条目数量")
    @GetMapping("state")
    public Result state(
            @RequestParam @ApiParam(value = "状态信息",required = true) String state,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量", required = false) Integer pageSize
    ) {
        List<Cart> carts;
        if(start == null || pageSize == null) {
            carts = service.findByState(state);
        } else {
            carts = service.findByState(state, start, pageSize);
        }
        Long count = service.getCountByState(state);
        Result res = Result.ok().data("items", carts);
        return res.data("count", count);
    }

    @ApiOperation("获取急救车的使用情况")
    @GetMapping("states")
    public Result states() {
//        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        Long sum = service.getCount();
        for (String state : service.getAllStates().keySet()) {
//            Map<String, String> map = new HashMap<>();
            Long count = service.getCountByState(state);
//            map.put("state", String.valueOf(state));
            long percentage = (count * 100) / sum;
            map.put(state, String.valueOf(percentage));
//            map.put("percentage", String.valueOf(percentage));
//            list.add(map);
        }
        return Result.ok().data("items", map);
    }

    @ApiOperation("获取急救车需要补充和更换药品的情况")
    @GetMapping("notification")
    public Result notification(
    ) {
        List<Drug> drugs = drugService.findAll();
        Map<CartExceptionEnum, HashSet<Long>> map = drugService.findException(drugs);
        // Todo 补充药品的功能还没做
        List<CartRes> exception = service.getException(map);
        return Result.ok().data("items", exception);
    }

    @ApiOperation("设置急救车为空闲状态")
    @GetMapping("free")
    public Result setFree(@RequestParam @ApiParam(value = "抢救车id", required = true) Long id) {
        Boolean res = service.setCartFree(id);
        return returnMessage(res);
    }

    @ApiOperation("设置急救车为补充药品状态")
    @GetMapping("inventory")
    public Result setInventory(@RequestParam @ApiParam(value = "抢救车id",required = true) Long id) {
        Boolean res = service.setCartInventory(id);
        return returnMessage(res);
    }

    @ApiOperation("设置急救车为急救状态")
    @GetMapping("emergency")
    public Result setEmergency(@RequestParam @ApiParam(value = "抢救车id", required = true) Long id) {
        Boolean res = service.setCartEmergency(id);
        return returnMessage(res);
    }

    @ApiOperation("添加急救车")
    @GetMapping("add")
    public Result add(@RequestParam @ApiParam(value = "部门id", required = true) Long departmentId) {
        Cart cart = new Cart(departmentId);
        Boolean res = service.add(cart);
        return returnMessage(res);
    }

    @ApiOperation("删除急救车")
    @GetMapping("remove")
    public Result remove(@RequestParam @ApiParam(value = "急救车id",required = true) Long id) {
        Boolean res = service.remove(id);
        return returnMessage(res);
    }

    @ApiOperation("修改急救车")
    @GetMapping("modify")
    public Result modify(
            @RequestParam @ApiParam(value = "急救车属性", required = true) Long id,
            @RequestParam @ApiParam(value = "急救车部门id", required = true) Long departmentId,
            @RequestParam(required = false) @ApiParam(value = "急救车状态", required = false) String state
            ) {
        Cart cart = service.findById(id);
        cart.setDepartmentId(departmentId);
        if(state != null && EnumUtils.isValidEnum(CartStateEnum.class, state)) {
            cart.setState(CartStateEnum.valueOf(state));
        }
        Boolean res = service.modify(cart);
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

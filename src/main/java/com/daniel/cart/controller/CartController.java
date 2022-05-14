package com.daniel.cart.controller;


import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.CartOperateLog;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.enums.CartExceptionEnum;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.res.CartRes;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.vo.PageVo;
import com.daniel.cart.mapper.CartOperateLogMapper;
import com.daniel.cart.service.CartService;
import com.daniel.cart.service.DrugService;
import com.daniel.cart.util.AttributeCheck;
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
public class CartController {

    private final CartService service;
    private final DrugService drugService;
    private final CartOperateLogMapper cartOperateLogMapper;
    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartController(CartService service, DrugService drugService, CartOperateLogMapper mapper) {
        this.service = service;
        this.drugService = drugService;
        this.cartOperateLogMapper = mapper;
    }

    @ApiOperation("查询急救车信息")
    @GetMapping("find")
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "抢救车id信息") Long id,
            @RequestParam(required = false) @ApiParam(value = "部门名称信息") String nameCondition,
            @RequestParam(required = false) @ApiParam(value = "状态信息") String state,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        if(id != null) {
            Cart cart = service.findById(id);
            return Result.ok().data("item", cart);
        }

        List<Cart> carts;
        Long count;
        if(AttributeCheck.isStringOk(nameCondition) && state != null) {
            count = service.getCountByLimit(nameCondition, state);
            carts = service.findByLimit(nameCondition, state, start, pageSize);
        } else if(AttributeCheck.isStringOk(nameCondition)) {
            carts = service.findByDepartment(nameCondition, start, pageSize);
            count = service.getCountByDepartment(nameCondition);
        } else if(state != null) {
            carts = service.findByState(state, start, pageSize);
            count = service.getCountByState(state);
        } else if(start != null && pageSize != null) {
            carts = service.findAll(start, pageSize);
            count = service.getCount();
        }else {
            carts = service.findAll();
            count = service.getCount();
        }
        return Result.ok().data("items", carts).data("count", count);
    }

    @ApiOperation("获取急救车操作日志")
    @GetMapping("log")
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        Long count = cartOperateLogMapper.getCount();
        List<CartOperateLog> logs;
        if(start != null && pageSize != null) {
            PageVo limit = new PageVo(start, pageSize);
            logs = cartOperateLogMapper.findByLimit(limit);
        } else {
            logs = cartOperateLogMapper.findAll();
        }
        return Result.ok().data("count", count).data("items", logs);
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
        return Result.ok().data("items", carts).data("count", count);
    }

    @ApiOperation("根据部门查询急救车信息以及信息条目数量")
    @GetMapping("depart")
    public Result depart(
            @RequestParam @ApiParam(value = "部门id信息",required = true) Long departmentId,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
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
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
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
        Map<String, String> map = new HashMap<>();
        Long sum = service.getCount();
        for (String state : service.getAllStates().keySet()) {
            Long count = service.getCountByState(state);
            long percentage = (count * 100) / sum;
            map.put(state, String.valueOf(percentage));
        }
        return Result.ok().data("items", map);
    }

    @ApiOperation("获取急救车需要补充和更换药品的情况")
    @GetMapping("notification")
    public Result notification(
    ) {
        List<Drug> drugs = drugService.findAll();
        Map<CartExceptionEnum, HashSet<Long>> map = drugService.findException(drugs);
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
            @RequestParam @ApiParam(value = "急救车 id", required = true) Long id,
            @RequestParam @ApiParam(value = "急救车部门id", required = true) Long departmentId,
            @RequestParam(required = false) @ApiParam(value = "急救车状态") String state
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
            logger.error("操作失败");
            return Result.error().message("操作失败");
        }
    }

}

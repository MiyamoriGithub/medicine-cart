package com.daniel.cart.controller;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.exception.DrugOperateException;
import com.daniel.cart.service.DrugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingAwareRunnable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "药品Controller", tags = {"药品信息管理接口"})
@RestController
@CrossOrigin
@RequestMapping("drug")
public class DrugController {

    private final DrugService service;
    private Logger logger = LoggerFactory.getLogger(DrugController.class);

    @Autowired
    public DrugController(DrugService service) {
        this.service = service;
    }

    @ApiOperation("查询全部药品信息")
    @GetMapping("find")
    public Result find(
            @RequestParam(value = "抢救车id信息", required = false) Long id,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        if(id != null) {
            Drug drug = service.findById(id);
            return Result.ok().data("item", drug);
        } else {

            List<Drug> carts = service.findAll();
            Long count = service.getCount();
            return Result.ok().data("items", carts).data("count", count);
        }
    }

    @ApiOperation("根据条码查询药品信息")
    @GetMapping("findByBarcode")
    public Result findByBarcode(@RequestParam("barcode") String barcode) {
        List<Drug> drugs = service.findByBarcode(barcode);
        if(drugs == null || drugs.size() == 0) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_INF_MISS_ERROR.getCode(), ResultCodeEnum.DRUG_INF_MISS_ERROR.getMessage());
        }
        return Result.ok().data("items", drugs);
    }



}

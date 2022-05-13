package com.daniel.cart.controller;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.DrugInfService;
import com.daniel.cart.service.DrugService;
import com.daniel.cart.util.AttributeCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * 药品信息管理接口
 *
 * @author Daniel Zheng
 **/

@Api(value = "药品通用信息Controller", tags = {"药品通用信息管理接口"})
@RestController
@CrossOrigin
@RequestMapping("drugInf")
public class DrugInfController{
    private final DrugInfService service;
    private final Logger logger = LoggerFactory.getLogger(DrugInfController.class);

    @Autowired
    public DrugInfController(DrugInfService service) {
        this.service = service;
    }

    @ApiOperation("查询药品通用信息")
    @GetMapping("find")
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "药品信息id") Long drugInfId,
            @RequestParam(required = false) @ApiParam(value = "条码") String barcode,
            @RequestParam(required = false) @ApiParam(value = "名称信息") String nameCondition,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        if (drugInfId != null) {
            DrugInf drugInf = service.findById(drugInfId);
            return Result.ok().data("item", drugInf);
        } else if(barcode != null) {
            DrugInf drugInf = service.findByBarcode(barcode);
            return Result.ok().data("item", drugInf);
        }

        List<DrugInf> drugInfs;
        Long count;
        if(AttributeCheck.isStringOk(nameCondition)) {
            if(start != null && pageSize != null) {
                drugInfs = service.findByName(nameCondition, start, pageSize);
            } else {
                drugInfs = service.findByName(nameCondition);
            }
            count = (long) drugInfs.size();
        } else{
            if (start != null && pageSize != null) {
                drugInfs = service.findAll(start, pageSize);
            } else {
                drugInfs = service.findAll();
            }
            count = service.getCount();
        }
        return Result.ok().data("items", drugInfs).data("count", count);
    }

    @ApiOperation("添加药品")
    @GetMapping("add")
    public Result add(
            @RequestParam @ApiParam(value = "名称", required = true) String name,
            @RequestParam @ApiParam(value = "条码", required = true) String barcode,
            @RequestParam @ApiParam(value = "保质期", required = true) Integer shelfLife,
            @RequestParam(required = false) @ApiParam(value = "包装规格") Integer drugPackage
    ) {
        DrugInf drugInf = new DrugInf(barcode, name, shelfLife);
        if(drugPackage != null) {
            drugInf.setDrugPackage(drugPackage);
        }
        Boolean res = service.add(drugInf);
        DrugInf inf = service.findByBarcode(barcode);
        return returnMessage(res).data("item", inf);
    }

    @ApiOperation("删除药品通用信息")
    @GetMapping("remove")
    public Result remove(@RequestParam @ApiParam(value = "待删除药品信息 id", required = true) Long drugInfId) {
        Boolean res = service.remove(drugInfId);
        return returnMessage(res);
    }

    @ApiOperation("修改药品")
    @GetMapping("modify")
    public Result modify(
            @RequestParam @ApiParam(value = "药品信息 id", required = true) Long drugInfId,
            @RequestParam(required = false) @ApiParam(value = "条形码") String barcode,
            @RequestParam(required = false) @ApiParam(value = "药品名称") String name,
            @RequestParam(required = false) @ApiParam(value = "药品保质期") Integer shelfLife,
            @RequestParam(required = false) @ApiParam(value = "药品包装规格") Integer drugPackage
    ) {
        DrugInf drugInf;
        drugInf = service.findById(drugInfId);
        // 如果不为空则更新 inf 信息到数据库
        if (drugPackage != null) {
            drugInf.setDrugPackage(drugPackage);
        }
        if (barcode != null) {
            drugInf.setBarcode(barcode);
        }
        if (name != null) {
            drugInf.setName(name);
        }
        if (shelfLife != null) {
            drugInf.setShelfLife(shelfLife);
        }
        Boolean res = service.modify(drugInf);
        return returnMessage(res);
    }

    private Result returnMessage(Boolean res) {
        if(res) {
            return Result.ok().data("msg", "操作成功");
        } else  {
            return Result.error().message("操作失败");
        }
    }
}

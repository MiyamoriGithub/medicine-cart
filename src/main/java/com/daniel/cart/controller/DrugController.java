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

@Api(value = "药品Controller", tags = {"药品管理接口"})
@RestController
@CrossOrigin
@RequestMapping("drug")
public class DrugController {

    private final DrugService service;
    private final DrugInfService drugInfService;
    private final Logger logger = LoggerFactory.getLogger(DrugController.class);

    @Autowired
    public DrugController(DrugService service, DrugInfService drugInfService) {
        this.service = service;
        this.drugInfService = drugInfService;
    }

    @ApiOperation("查询药品信息")
    @GetMapping("find")
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "药品id") Long id,
            @RequestParam(required = false) @ApiParam(value = "条码") String barcode,
            @RequestParam(required = false) @ApiParam(value = "名称信息") String nameCondition,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        if (id != null) {
            Drug drug = service.findById(id);
            return Result.ok().data("item", drug);
        }
        List<Drug> drugs;
        Long count;
        if(barcode != null) {
            if(start != null && pageSize != null) {
                drugs = service.findByBarcode(barcode, start, pageSize);
            } else {
                drugs = service.findByBarcode(barcode);
            }
            count = (long) drugs.size();
        } else if(AttributeCheck.isStringOk(nameCondition)) {
            if(start != null && pageSize != null) {
                drugs = service.findByName(nameCondition, start, pageSize);
            } else {
                drugs = service.findByName(nameCondition);
            }
            count = (long) drugs.size();
        } else{
            if (start != null && pageSize != null) {
                drugs = service.findAll(start, pageSize);
            } else {
                drugs = service.findAll();
            }
            count = service.getCount();
        }
        return Result.ok().data("items", drugs).data("count", count);
    }

    @ApiOperation("根据条码查询药品信息")
    @GetMapping("barcode")
    public Result barcode(
            @RequestParam @ApiParam(value = "药品条码", required = true) String barcode,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        List<Drug> drugs;
        if (start != null && pageSize != null) {
            drugs = service.findByBarcode(barcode, start, pageSize);
        } else {
            drugs = service.findByBarcode(barcode);
        }
        Long count = service.getCountByBarcode(barcode);
        return Result.ok().data("items", drugs).data("count", count);
    }

    @ApiOperation("查询临期药品信息")
    @GetMapping("temporary")
    public Result temporary(
            @RequestParam(required = false) @ApiParam(value = "药品条码") String barcode,
            @RequestParam(required = false) @ApiParam(value = "药品名称") String name
    ) {
        List<Drug> temporaryDrugs;
        if (barcode != null) {
            List<Drug> drugs = service.findByBarcode(barcode);
            temporaryDrugs = service.findTemporary(drugs);
        } else if (name != null) {
            List<Drug> drugs = service.findByName(name);
            temporaryDrugs = service.findTemporary(drugs);
        } else {
            temporaryDrugs = service.findTemporary();
        }
        return Result.ok().data("items", temporaryDrugs).data("count", temporaryDrugs.size());
    }

    @ApiOperation("查询过期药品信息")
    @GetMapping("expire")
    public Result expire(
            @RequestParam(required = false) @ApiParam(value = "药品条码") String barcode,
            @RequestParam(required = false) @ApiParam(value = "药品名称") String name
    ) {
        List<Drug> expireDrugs;
        if (barcode != null) {
            List<Drug> drugs = service.findByBarcode(barcode);
            expireDrugs = service.findExpire(drugs);
        } else if (name != null) {
            List<Drug> drugs = service.findByName(name);
            expireDrugs = service.findExpire(drugs);
        } else {
            expireDrugs = service.findExpire();
        }
        return Result.ok().data("items", expireDrugs).data("count", expireDrugs.size());
    }

    @ApiOperation("添加药品")
    @GetMapping("add")
    public Result add(
            @RequestParam(required = false) @ApiParam(value = "药品信息 id，如果是新药品则需要添加其他药品信息") Long drugInfId,
            @RequestParam @ApiParam(value = "生产日期",required = true) Timestamp productDate,
            @RequestParam(required = false) @ApiParam(value = "库存") Integer stock,
            @RequestParam(required = false) @ApiParam(value = "条码") String barcode,
            @RequestParam(required = false) @ApiParam(value = "名称") String name,
            @RequestParam(required = false) @ApiParam(value = "保质期") Integer shelfLife,
            @RequestParam(required = false) @ApiParam(value = "包装规格") Integer drugPackage
            ) {
        // 首先通过 drugInf id 或者条形码查询 drugInf 信息，如果不存在就存入新的drugInf
        DrugInf drugInf = null;
        if(drugInfId != null) {
            drugInf = drugInfService.findById(drugInfId);
        } else if(barcode != null) {
            drugInf = drugInfService.findByBarcode(barcode);
        }
        if(drugInf == null){
            drugInf = new DrugInf();
            drugInf.setBarcode(barcode);
            drugInf.setName(name);
            drugInf.setShelfLife(shelfLife);
            drugInf.setDrugPackage(drugPackage);
            drugInfService.add(drugInf);
            drugInf = drugInfService.findByBarcode(barcode);
        }

        // 存入新的 drug 信息
        Drug drug = new Drug();
        // 注意这里不能直接存传参传入的 id，因为存入数据库可能会产生新 id 信息
        drug.setDrugInfId(drugInf.getDrugInfId());
        drug.setStock(stock);
        drug.setProductDate(productDate);
        Boolean res = service.add(drug);
        return returnMessage(res);
    }

    @ApiOperation("删除药品")
    @GetMapping("remove")
    public Result remove(@RequestParam @ApiParam(value = "待删除药品 id", required = true) Long id) {
        Boolean res = service.remove(id);
        return returnMessage(res);
    }

    @ApiOperation("修改药品")
    @GetMapping("modify")
    public Result modify(
            @RequestParam @ApiParam(value = "药品 id", required = true) Long id,
            @RequestParam(required = false) @ApiParam(value = "新的药品信息 id") Long drugInfId,
            @RequestParam(required = false) @ApiParam(value = "新的药品库存") Integer stock,
            @RequestParam(required = false) @ApiParam(value = "新的药品生产日期") Timestamp productDate,
            @RequestParam(required = false) @ApiParam(value = "条形码") String barcode,
            @RequestParam(required = false) @ApiParam(value = "药品名称") String name,
            @RequestParam(required = false) @ApiParam(value = "药品保质期") Integer shelfLife,
            @RequestParam(required = false) @ApiParam(value = "药品包装规格") Integer drugPackage
    ) {
        DrugInf drugInf;
        if(drugInfId != null) {
            drugInf = drugInfService.findById(drugInfId);
            // 如果药品信息 id 不为空且数据库中没有对应的信息，则添加新的 inf 信息到数据库
            if(drugInf == null) {
                drugInf = new DrugInf();
                drugInf.setDrugPackage(drugPackage);
                drugInf.setName(name);
                drugInf.setBarcode(barcode);
                drugInf.setShelfLife(shelfLife);
                drugInfService.add(drugInf);
            } else {
                // 如果不为空则更新 inf 信息到数据库
                if(drugPackage != null) {
                    drugInf.setDrugPackage(drugPackage);
                }
                if(barcode != null) {
                    drugInf.setBarcode(barcode);
                }
                if(name != null) {
                    drugInf.setName(name);
                }
                if(shelfLife != null) {
                    drugInf.setShelfLife(shelfLife);
                }
                drugInfService.modify(drugInf);
            }
        }

        // 从数据库中获取 drug 实体类，将不为空的信息添加进去并更新到数据库
        Drug drug = service.findById(id);
        if(drugInfId != null && !drugInfId.equals(drug.getDrugInfId())) {
            drug.setDrugInfId(drugInfId);
        }
        if(stock != null) {
            drug.setStock(stock);
        }
        if(productDate != null) {
            drug.setProductDate(productDate);
        }
        Boolean res = service.modify(drug);
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
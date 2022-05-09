package com.daniel.cart.controller;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.DrugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Api(value = "药品Controller", tags = {"药品管理接口"})
@RestController
@CrossOrigin
@RequestMapping("drug")
public class DrugController implements AbstractController {

    private final DrugService service;
    private final Logger logger = LoggerFactory.getLogger(DrugController.class);

    @Autowired
    public DrugController(DrugService service) {
        this.service = service;
    }

    @ApiOperation("查询全部药品信息")
    @GetMapping("find")
    @Override
    public Result find(
            @RequestParam(value = "药品id信息", required = false) Long id,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量", required = false) Integer pageSize
    ) {
        List<Drug> drugs;
        if (id != null) {
            Drug drug = service.findById(id);
            return Result.ok().data("item", drug);
        } else if (start != null && pageSize != null) {
            drugs = service.findAll(start, pageSize);
        } else {
            drugs = service.findAll();
        }
        Long count = service.getCount();
        return Result.ok().data("items", drugs).data("count", count);
    }

    @ApiOperation("根据条码查询药品信息")
    @GetMapping("barcode")
    public Result barcode(
            @RequestParam("药品条码") String barcode,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量", required = false) Integer pageSize
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

    @ApiOperation("根据名称查询药品信息")
    @GetMapping("name")
    public Result name(
            @RequestParam("药品名称查询条件") String name,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量", required = false) Integer pageSize
    ) {
        List<Drug> drugs;
        if (start != null && pageSize != null) {
            drugs = service.findByName(name, start, pageSize);
        } else {
            drugs = service.findByName(name);
        }
        Long count = service.getCountByName(name);
        return Result.ok().data("items", drugs).data("count", count);
    }

    @ApiOperation("查询临期药品信息")
    @GetMapping("temporary")
    public Result temporary(
            @RequestParam(value = "药品条码", required = false) String barcode,
            @RequestParam(value = "药品名称", required = false) String name
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
            @RequestParam(value = "药品条码", required = false) String barcode,
            @RequestParam(value = "药品名称", required = false) String name
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
            @RequestParam(value = "药品信息 id，如果是新药品则需要添加其他药品信息") Long drugInfId,
            @RequestParam(value = "生产日期") Date productDate,
            @RequestParam(value = "库存", required = false) Integer stock,
            @RequestParam(value = "条码", required = false) String barcode,
            @RequestParam(value = "名称", required = false) String name,
            @RequestParam(value = "保质期", required = false) Integer shelfLife,
            @RequestParam(value = "包装规格", required = false) Integer drugPackage
            ) {
        // Todo 添加逻辑：如果药品id存在则直接查询数据库并封装
        DrugInf drugInf = new DrugInf();
        drugInf.setDrugInfId(drugInfId);
        drugInf.setDrugPackage(drugPackage);
        drugInf.setBarcode(barcode);
        drugInf.setName(name);
        drugInf.setShelfLife(shelfLife);
        Drug drug = new Drug();
        drug.setDrugInfId(drugInfId);
        drug.setDrugInf(drugInf);
        drug.setStock(stock);
        drug.setProductDate(productDate);
        Boolean res = service.addDrug(drug);
        if (res) {
            return Result.ok().data("msg", "操作成功");
        } else {
            return Result.error().message("操作失败");
        }
    }

    @ApiOperation("删除药品")
    @GetMapping("remove")
    public Result remove(@RequestParam("待删除药品 id") Long id) {
        Boolean res = service.deleteDrug(id);
        if (res) {
            return Result.ok().data("msg", "操作成功");
        } else {
            return Result.error().message("操作失败");
        }
    }

    @ApiOperation("修改药品")
    @GetMapping("modify")
    public Result modify(
            @RequestParam("药品 id") Long id,
            @RequestParam(value = "新的药品信息 id", required = false) Long drugInfId,
            @RequestParam(value = "新的药品库存", required = false) Integer stock,
            @RequestParam(value = "新的药品生产日期", required = false) Date productDate
    ) {
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
        Boolean res = service.modifyDrug(drug);
        if(res) {
            return Result.ok().data("msg", "操作成功");
        } else  {
            return Result.error().message("操作失败");
        }
    }
}
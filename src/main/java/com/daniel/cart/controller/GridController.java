package com.daniel.cart.controller;

import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.DrugInfService;
import com.daniel.cart.service.GridService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Grid 管理接口
 *
 * @author Daniel Zheng
 **/

@Api(value = "Grid Controller", tags = {"Grid 管理接口"})
@RestController
@CrossOrigin
@RequestMapping("grid")
public class GridController implements AbstractController{

    private final GridService service;
    private final DrugInfService drugInfService;
    private final Logger logger = LoggerFactory.getLogger(GridController.class);

    @Autowired
    public GridController(GridService service, DrugInfService drugInfService) {
        this.service = service;
        this.drugInfService = drugInfService;
    }

    @ApiOperation("查询 Grid 信息")
    @GetMapping("find")
    @Override
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "Grid id信息", required = false) Long id,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        if(id != null) {
            Grid grid = service.findById(id);
            return Result.ok().data("item", grid);
        } else if(start != null && pageSize != null) {
            List<Grid> grids = service.findAll(start, pageSize);
            Long count = service.getCount();
            return Result.ok().data("items", grids).data("count", count);
        }else {
            List<Grid> grids = service.findAll();
            Long count = service.getCount();
            return Result.ok().data("items", grids).data("count", count);
        }
    }

    @ApiOperation("通过抢救车 id 获取 grid 信息以及条目数量")
    @GetMapping("cart")
    public Result cart(
            @RequestParam @ApiParam(value = "急救车的 Id 信息", required = true) Long cartId,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        List<Grid> grids;
        if(start != null && pageSize != null) {
            grids = service.findByCart(cartId, start, pageSize);
        } else {
            grids = service.findByCart(cartId);
        }
        Long count = service.getCountByCart(cartId);
        return Result.ok().data("grids", grids).data("count", count);
    }

    @ApiOperation("获取指定抢救车及其层数的全部 grid 信息")
    @GetMapping("layer")
    public Result layer(
            @RequestParam @ApiParam(value = "急救车的 Id 信息", required = true) Long cartId,
            @RequestParam @ApiParam(value = "层数信息", required = true) Integer layer,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        List<Grid> grids;
        if(start != null && pageSize != null) {
            grids = service.findByLayer(cartId, layer, start, pageSize);
        } else {
            grids = service.findByLayer(cartId, layer);
        }
        Long count = service.getCountByLayer(cartId, layer);
        return Result.ok().data("grids", grids).data("count", count);
    }

    @ApiOperation("根据位置信息获取 Grid 信息")
    @GetMapping("posit")
    public Result posit(
            @RequestParam("急救车的 Id") Long cartId,
            @RequestParam("层数") Integer layer,
            @RequestParam("行") Integer row,
            @RequestParam("列") Integer column
    ) {
        Grid grid = service.findByPosit(cartId, layer, row, column);
        return Result.ok().data("item", grid);
    }

    @ApiOperation("新增 Grid 信息")
    @GetMapping("add")
    public Result add(
            @RequestParam @ApiParam(value = "急救车的 Id", required = true) Long cartId,
            @RequestParam @ApiParam(value = "层数", required = true) Integer layer,
            @RequestParam @ApiParam(value = "行",required = true) Integer row,
            @RequestParam @ApiParam(value = "列", required = true) Integer column,
            @RequestParam(required = false) @ApiParam(value = "药品信息id（无效的 id 将被忽略）", required = false) Long drugInfId,
            @RequestParam(required = false) @ApiParam (value = "Grid 容量（默认为 8）", required = false) Integer capacity
    ) {
        Grid grid = new Grid();
        grid.setCartId(cartId);
        grid.setLayer(layer);
        grid.setRow(row);
        grid.setColumn(column);
        if(capacity != null) {
            grid.setCapacity(capacity);
        }
        if(drugInfId != null) {
            if(drugInfService.findById(drugInfId) != null) {
                grid.setDrugInfId(drugInfId);
            }
        }
        Boolean res = service.add(grid);
        if(res) {
            logger.info("新增 grid 信息：" + grid);
        }
        return returnMessage(res);
    }

    @ApiOperation("修改 Grid 中存放的药品信息")
    @GetMapping("modify")
    public Result modify(
            @RequestParam @ApiParam(value = "Grid id", required = true) Long id,
            @RequestParam(required = false) @ApiParam(value = "药品信息id（无效的 id 将被忽略）", required = false) Long drugInfId
    ) {
        Grid grid = service.findById(id);
        grid.setDrugInfId(drugInfId);
        Boolean res = service.modify(grid);
        if(res) {
            logger.info("grid 信息被修改：" + grid);
        }
        return returnMessage(res);
    }

    @ApiOperation("删除指定的 Grid")
    @GetMapping("remove")
    public Result remove(@RequestParam @ApiParam(value = "待删除的 Grid id", required = true) Long id) {
        Grid grid = service.findById(id);
        Boolean res = service.remove(id);
        if(res) {
            logger.info("grid 信息被删除" + grid);
        }
        return returnMessage(res);
    }

    @ApiOperation("查找需要补充药品的 Grid")
    @GetMapping("findInventory")
    public Result findInventory(
            @RequestParam(required = false) @ApiParam(value = "急救车 id", required = false) Long cartId,
            @RequestParam(required = false) @ApiParam(value = "急救车层数", required = false) Integer layer
    ) {
        List<Grid> res;
        if(cartId != null && layer != null) {
            List<Grid> list = service.findByLayer(cartId, layer);
            res = service.findNeedInventory(list);
        }else if(cartId != null) {
            List<Grid> grids = service.findByCart(cartId);
            res = service.findNeedInventory(grids);
        } else {
            res = service.findNeedInventory();
        }
        return Result.ok().data("items", res).data("count", res.size());
    }


    private Result returnMessage(Boolean res) {
        if(res) {
            return Result.ok().message("操作成功");
        } else {
            return Result.error().message("操作失败");
        }
    }
}

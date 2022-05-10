package com.daniel.cart.controller;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.BlockService;
import com.daniel.cart.service.DrugInfService;
import com.daniel.cart.service.DrugService;
import com.daniel.cart.service.GridService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 抢救车常用接口集合
 *
 * @author Daniel Zheng
 **/

@Api(value = "Grid Controller", tags = {"抢救车常用接口集合"})
@RestController
@CrossOrigin
@RequestMapping("react")
public class ReactController {

    private final DrugService drugService;
    private final BlockService blockService;
    private final GridService gridService;
    private final DrugInfService drugInfService;
    private final Logger logger;

    {
        logger = LoggerFactory.getLogger(ReactController.class);
    }

    @Autowired
    public ReactController(DrugService drugService, BlockService blockService, GridService gridService, DrugInfService drugInfService) {
        this.drugService = drugService;
        this.blockService = blockService;
        this.gridService = gridService;
        this.drugInfService = drugInfService;
    }

    @ApiOperation("根据 id 查询药品信息")
    @GetMapping("findDrug")
    public Result findDrug(
            @RequestParam @ApiParam(value = "药品 id", required = true) Long id
    ) {
        Drug drug = drugService.findById(id);
        return Result.ok().data("drug", drug);
    }

    @ApiOperation("根据药品 id 和 急救车 id 查询 grid 信息")
    @GetMapping("findPosit")
    public Result findPosit(
            @RequestParam @ApiParam(value = "药品id信息", required = true) Long id,
            @RequestParam @ApiParam(value = "急救车 id", required = true) Long cartId
            ) {
        Drug drug = drugService.findById(id);
        Grid res = null;
        for (Grid grid : gridService.findByCart(cartId)) {
            if(Objects.equals(grid.getDrugInfId(), drug.getDrugInfId())) {
                res = grid;
            }
        }
        if(res != null) {
            return Result.ok().data("grid", res);
        } else {
            return Result.error().message("未查询到对应信息");
        }
    }

    @GetMapping("getInfInGrid")
    @ApiOperation("查询指定位置存储的药品信息")
    public Result getInf(
            @RequestParam @ApiParam(value = "急救车 id", required = true) Long cartId,
            @RequestParam @ApiParam(value = "层数", required = true) Integer layer,
            @RequestParam @ApiParam(value = "行", required = true) Integer row,
            @RequestParam @ApiParam(value = "列",required = true) Integer column
    ) {
        Grid grid = gridService.findByPosit(cartId, layer, row, column);
        DrugInf drugInf = drugInfService.findById(grid.getDrugInfId());
        if(drugInf != null) {
//            List<Block> blocks = blockService.findByGrid(grid.getId());
//            return Result.ok().data("item", drugInf).data("items", blocks);
            return Result.ok().data("drugInf", drugInf);
        } else {
            return Result.error().message("未查询到对应信息");
        }
    }


    @GetMapping("getInfInBlock")
    @ApiOperation("查询指定位置存储的药品信息")
    public Result getInf(
            @RequestParam @ApiParam(value = "急救车 id", required = true) Long cartId,
            @RequestParam @ApiParam(value = "层数", required = true) Integer layer,
            @RequestParam @ApiParam(value = "行", required = true) Integer row,
            @RequestParam @ApiParam(value = "列", required = true) Integer column,
            @RequestParam @ApiParam(value = "序列", required = true) Integer serial
    ) {
        Grid grid = gridService.findByPosit(cartId, layer, row, column);
        Block block = blockService.findByPosit(grid, serial);
        Drug drug = drugService.findById(block.getDrugId());
        if(drug != null) {
            return Result.ok().data("drug", drug);
        } else {
            return Result.error().message("未查询到对应信息");
        }
    }

    @ApiOperation("向指定位置存入药品")
    @GetMapping("deposit")
    public Result deposit(
            @RequestParam @ApiParam(value = "急救车 id", required = true) Long cartId,
            @RequestParam @ApiParam(value = "层数", required = true) Integer layer,
            @RequestParam @ApiParam(value = "行", required = true) Integer row,
            @RequestParam @ApiParam(value = "列", required = true) Integer column,
            @RequestParam @ApiParam(value = "序列", required = true) Integer serial,
            @RequestParam @ApiParam(value = "药品 id", required = true) Long drugId
    ) {
        Grid grid = gridService.findByPosit(cartId, layer, row, column);
        Block block = blockService.findByPosit(grid, serial);
        Drug drug = drugService.findById(drugId);
        Boolean res = blockService.depositDrug(block, drug);
        List<Block> blocks = blockService.findByGrid(grid.getId());
        return returnMessage(res).data("items", blocks);
    }

    @ApiOperation("取出指定位置的药品")
    @GetMapping("withdraw")
    public Result withdraw(
            @RequestParam @ApiParam(value = "急救车 id", required = true) Long cartId,
            @RequestParam @ApiParam(value = "层数", required = true) Integer layer,
            @RequestParam @ApiParam(value = "行", required = true) Integer row,
            @RequestParam @ApiParam(value = "列", required = true) Integer column,
            @RequestParam @ApiParam(value = "序列", required = true) Integer serial
    ) {
        Grid grid = gridService.findByPosit(cartId, layer, row, column);
        logger.info(grid.toString());
        Block block = blockService.findByPosit(grid, serial);
        logger.info(block.toString());
        Boolean res = blockService.removeDrug(block.getId());
        List<Block> blocks = blockService.findByGrid(grid.getId());
        return returnMessage(res).data("items", blocks);
//        return returnMessage(res);
    }

    private Result returnMessage(Boolean res) {
        if(res) {
            return Result.ok().message("操作成功");
        } else {
            return Result.error().message("操作失败");
        }
    }
}
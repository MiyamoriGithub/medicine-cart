package com.daniel.cart.controller;

import com.daniel.cart.domain.*;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.vo.PageVo;
import com.daniel.cart.mapper.DrugOperateLogMapper;
import com.daniel.cart.service.BlockService;
import com.daniel.cart.service.DrugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

/**
 * Block 管理接口
 *
 * @author Daniel Zheng
 **/

// Todo 测试接口中的全部方法

@Api(value = "Block Controller", tags = {"Block 管理接口"})
@RestController
@CrossOrigin
@RequestMapping("block")
public class BlockController implements AbstractController {

    private final BlockService service;
    private final DrugService drugService;
    private final DrugOperateLogMapper drugOperateLogMapper;
    private final Logger logger = LoggerFactory.getLogger(BlockController.class);

    @Autowired
    public BlockController(BlockService service, DrugService drugService, DrugOperateLogMapper mapper) {
        this.service = service;
        this.drugService = drugService;
        this.drugOperateLogMapper = mapper;
    }

    @ApiOperation("查询 block 信息")
    @GetMapping("find")
    @Override
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "block id 信息") Long id,
            @RequestParam(required = false) @ApiParam(value = "起始条目") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize) {
        if(id != null) {
            Block block = service.findById(id);
            return Result.ok().data("item", block);
        } else if (start != null && pageSize != null) {
            List<Block> blocks = service.findAll(start, pageSize);
            Long count = service.getCount();
            return Result.ok().data("items", blocks).data("count", count);
        }else {
            List<Block> blocks = service.findAll();
            Long count = service.getCount();
            return Result.ok().data("items", blocks).data("count", count);
        }
    }

    @ApiOperation("获取药品操作日志")
    @GetMapping("log")
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        Long count = drugOperateLogMapper.getCount();
        List<DrugOperateLog> logs;
        if(start != null && pageSize != null) {
            PageVo limit = new PageVo(start, pageSize);
            logs = drugOperateLogMapper.findByLimit(limit);
        } else {
            logs = drugOperateLogMapper.findAll();
        }
        return Result.ok().data("items", logs).data("count", count);
    }

    @ApiOperation("通过药品 id 信息查询 block 信息")
    @GetMapping("drug")
    public Result drug(@RequestParam @ApiParam(value = "药品 id",required = true) Long drugId,
                       @RequestParam(required = false) @ApiParam(value = "起始条目") Integer start,
                       @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize) {
        List<Block> blocks;
        if(start != null && pageSize != null) {
            blocks = service.findByDrug(drugId, start, pageSize);
        } else {
            blocks = service.findByDrug(drugId);
        }
        Long count = service.getCountByDrug(drugId);
        return Result.ok().data("items", blocks).data("count", count);
    }

    @ApiOperation("通过 grid id 获取 block 信息")
    @GetMapping("grid")
    public Result grid(@RequestParam @ApiParam(value = "grid id",required = true) Long gridId,
                       @RequestParam(required = false) @ApiParam(value = "起始条目") Integer start,
                       @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        List<Block> blocks;
        if(start != null && pageSize != null) {
            blocks = service.findByGrid(gridId, start, pageSize);
        } else {
            blocks = service.findByGrid(gridId);
        }
        Long count = service.getCountByGrid(gridId);
        return Result.ok().data("items", blocks).data("count", count);
    }

    @ApiOperation("通过位置信息获取 Block")
    @GetMapping("posit")
    public Result posit(@RequestParam @ApiParam(value = "急救车 id", required = true) Long cartId,
                       @RequestParam(required = false) @ApiParam(value = "层数") Integer layer,
                       @RequestParam(required = false) @ApiParam(value = "行") Integer row,
                       @RequestParam(required = false) @ApiParam(value = "列") Integer column,

                       @RequestParam(required = false) @ApiParam(value = "起始条目") Integer start,
                       @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {

        List<Block> blocks;
        Long count;
        // 如果位置信息齐全就查 grid 对应的 block
        if(cartId != null && layer != null && row != null && column != null) {
            Grid grid = new Grid(cartId, layer, row, column);
            count = service.getCountByGrid(grid);
            if(start != null && pageSize != null) {
                blocks = service.findByGrid(grid, start, pageSize);
            } else {
                blocks = service.findByGrid(grid);
            }
        } else if(cartId != null && layer != null) {
            // 只有 cart 和 layer
            count = service.getCountByLayer(cartId, layer);
            if(start != null && pageSize != null) {
                blocks = service.findByLayer(cartId, layer, start, pageSize);
            } else {
                blocks = service.findByLayer(cartId, layer);
            }
        } else if(cartId != null){
            // 只有 cart
            count = service.getCountByCart(cartId);
            if(start != null && pageSize != null) {
                blocks = service.findByCart(cartId, start, pageSize);
            } else {
                blocks = service.findByCart(cartId);
            }
        } else {
            // 都没有
            return Result.error().message("没有位置信息");
        }
        return Result.ok().data("items", blocks).data("count", count);
    }

    @ApiOperation("添加 block")
    @GetMapping("add")
    public Result add(
            @RequestParam @ApiParam(value = "所属 grid 信息",required = true) Long gridId,
            @RequestParam(required = false) @ApiParam(value = "存放的药品信息") Long drugId
    ) {
        Block block = new Block();
        block.setGridId(gridId);
        if(drugId != null) {
            block.setDrugId(drugId);
        }
        Boolean res = service.add(block);
        return returnMessage(res);
    }

    @ApiOperation("删除 block")
    @GetMapping("remove")
    public Result remove(
            @RequestParam @ApiParam(value = "待删除的 block id",required = true) Long blockId) {
        Boolean res = service.remove(blockId);
        return returnMessage(res);
    }

    @ApiOperation("查询指定 block 中的药品信息")
    @GetMapping("getDrugInBlock")
    public Result getDrugInBlock(
            @RequestParam @ApiParam(value = "block id",required = true) Long blockId
    ) {
        Drug drug = service.getDrugInBlock(blockId);
        return Result.ok().data("item", drug);
    }

    @ApiOperation("判断 Block 是否为空")
    @GetMapping("isBlockEmpty")
    public Result isBlockEmpty(
            @RequestParam @ApiParam(value = "block id",required = true) Long blockId
    ) {
         return Result.ok().data("res", service.isBlockEmpty(blockId));
    }

    @ApiOperation("向指定 block 中存入指定药品")
    @GetMapping("depositDrug")
    public Result depositDrug(
            @RequestParam @ApiParam(value = "block id", required = true) Long blockId,
            @RequestParam(required = false) @ApiParam(value = "药品 id") Long drugId,
            @RequestParam(required = false) @ApiParam(value = "条形码，将存入对应条形码的最新生产日期药品") String barcode
    ) {

        Drug drug = null;
        if(drugId != null) {
            drug = drugService.findById(drugId);
        }
        if(drugId == null && barcode != null) {
            List<Drug> list = drugService.findByBarcode(barcode);
            list.sort(Comparator.comparing(Drug::getProductDate));
            for (Drug d : list) {
                logger.info(d.toString());
            }
            drug = list.get(list.size() - 1);
        }
        if(drug == null) {
            return Result.error().message("无法获取药品信息");
        }
        Boolean res = service.depositDrug(service.findById(blockId), drug);
        return returnMessage(res);
    }

    @ApiOperation("移除指定 block 中的药品")
    @GetMapping("removeDrug")
    public Result removeDrug(@RequestParam @ApiParam(value = "block id", required = true) Long blockId) {
        Boolean res = service.removeDrug(blockId);
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

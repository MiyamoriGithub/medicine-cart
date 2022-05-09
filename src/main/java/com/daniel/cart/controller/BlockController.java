package com.daniel.cart.controller;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.BlockService;
import com.daniel.cart.service.DrugService;
import com.daniel.cart.service.GridService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private final GridService gridService;
    private final DrugService drugService;
    private final Logger logger = LoggerFactory.getLogger(BlockController.class);

    @Autowired
    public BlockController(BlockService service, GridService gridService, DrugService drugService) {
        this.service = service;
        this.gridService = gridService;
        this.drugService = drugService;
    }

    @ApiOperation("查询 block 信息")
    @GetMapping("find")
    @Override
    public Result find(
            @RequestParam(value = "block id 信息", required = false) Long id,
            @RequestParam(value = "起始条目", required = false) Integer start,
            @RequestParam(value = "每页信息数量", required = false) Integer pageSize) {
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

    @ApiOperation("通过药品 id 信息查询 block 信息")
    @GetMapping("drug")
    public Result drug(@RequestParam("药品 id") Long drugId,
                       @RequestParam(value = "起始条目", required = false) Integer start,
                       @RequestParam(value = "每页信息数量", required = false) Integer pageSize) {
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
    public Result grid(@RequestParam("grid id") Long gridId,
                       @RequestParam(value = "起始条目", required = false) Integer start,
                       @RequestParam(value = "每页信息数量", required = false) Integer pageSize
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
    public Result posit(@RequestParam("急救车 id") Long cartId,
                       @RequestParam(value = "层数",required = false) Integer layer,
                       @RequestParam(value = "行", required = false) Integer row,
                       @RequestParam(value = "列", required = false) Integer column,

                       @RequestParam(value = "起始条目", required = false) Integer start,
                       @RequestParam(value = "每页信息数量", required = false) Integer pageSize
    ) {

        List<Block> blocks;
        Long count;
        if(cartId != null && layer != null && row != null && column != null) {
            Grid grid = new Grid(cartId, layer, row, column);
            count = service.getCountByGrid(grid);
            if(start != null && pageSize != null) {
                blocks = service.findByGrid(grid, start, pageSize);
            } else {
                blocks = service.findByGrid(grid);
            }
        } else if(cartId != null && layer != null) {
            count = service.getCountByLayer(cartId, layer);
            if(start != null && pageSize != null) {
                blocks = service.findByLayer(cartId, layer, start, pageSize);
            } else {
                blocks = service.findByLayer(cartId, layer);
            }
        } else if(cartId != null){
            count = service.getCountByCart(cartId);
            if(start != null && pageSize != null) {
                blocks = service.findByCart(cartId, start, pageSize);
            } else {
                blocks = service.findByCart(cartId);
            }
        } else {
            count = service.getCount();
            if(start != null && pageSize != null) {
                blocks = service.findAll(start, pageSize);
            } else {
                blocks = service.findAll();
            }
        }
        return Result.ok().data("items", blocks).data("count", count);
    }

    @ApiOperation("添加 block")
    @GetMapping("add")
    public Result add(
            @RequestParam("所属 grid 信息") Long gridId,
            @RequestParam(value = "存放的药品信息", required = false) Long drugId
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
    public Result remove(Long blockId) {
        Boolean res = service.remove(blockId);
        return returnMessage(res);
    }

    @ApiOperation("查询指定 block 中的药品信息")
    @GetMapping("getDrugInBlock")
    public Result getDrugInBlock(@RequestParam("block id") Long blockId) {
        Drug drug = service.getDrugInBlock(blockId);
        return Result.ok().data("item", drug);
    }

    @ApiOperation("判断 Block 是否为空")
    @GetMapping("isBlockEmpty")
    public Result isBlockEmpty(Long blockId) {
         return Result.ok().data("res", service.isBlockEmpty(blockId));
    }

    @ApiOperation("向指定 block 中存入指定药品")
    @GetMapping("depositDrug")
    public Result depositDrug(
            @RequestParam("block id") Long blockId,
            @RequestParam(value = "药品 id", required = false) Long drugId,
            @RequestParam(value = "条形码，将存入对应条形码的最新生产日期药品", required = false) String barcode
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
    public Result removeDrug(@RequestParam("block id") Long blockId) {
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

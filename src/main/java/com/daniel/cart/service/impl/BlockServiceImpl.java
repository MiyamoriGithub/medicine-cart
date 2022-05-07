package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.vo.BlockVo;
import com.daniel.cart.exception.BlockOperateException;
import com.daniel.cart.exception.GridOperateException;
import com.daniel.cart.mapper.BlockMapper;
import com.daniel.cart.mapper.DrugMapper;
import com.daniel.cart.mapper.GridMapper;
import com.daniel.cart.service.BlockService;
import com.daniel.cart.util.AttributeCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BlockService 接口的实现类
 *
 * @author Daniel Zheng
 **/

@Service("blockService")
@Transactional
public class BlockServiceImpl implements BlockService {

    private final BlockMapper mapper;
    private final GridMapper gridMapper;
    private final DrugMapper drugMapper;

    public BlockServiceImpl(BlockMapper mapper, GridMapper gridMapper, DrugMapper drugMapper) {
        this.mapper = mapper;
        this.gridMapper = gridMapper;
        this.drugMapper = drugMapper;
    }

    @Override
    public List<Block> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Block> findAllByDrug(Drug drug) {
        BlockVo limit = setLimit(drug);
        return mapper.findAllByLimit(limit);
    }

    @Override
    public List<Block> findAllByDrug(Long drugId) {
        BlockVo limit = new BlockVo();
        limit.setDrugId(drugId);
        return mapper.findAllByLimit(limit);
    }

    @Override
    public List<Block> findAllByGrid(Grid grid) {
        BlockVo limit = setLimit(grid);
        return mapper.findAllByLimit(limit);
    }

    @Override
    public List<Block> findAllByGrid(Long gridId) {
        BlockVo limit = new BlockVo();
        limit.setGridId(gridId);
        return mapper.findAllByLimit(limit);
    }

    @Override
    public Block findById(Long id) {
        if(id == null || id <= 0L) {
            throw new BlockOperateException(25001, "药品信息缺失");
        }
        return mapper.findById(id);
    }

    @Override
    public Long getCountByDrug(Drug drug) {
        BlockVo limit = setLimit(drug);
        return mapper.getCountByLimit(limit);
    }

    @Override
    public Long getCountByDrug(Long drugId) {
        BlockVo limit = new BlockVo();
        limit.setDrugId(drugId);
        return mapper.getCountByLimit(limit);
    }


    @Override
    public Long getCountByGrid(Grid grid) {
        BlockVo limit = setLimit(grid);
        return mapper.getCountByLimit(limit);
    }

    public Long getCountByGrid(Long gridId) {
        BlockVo limit = new BlockVo();
        limit.setGridId(gridId);
        return mapper.getCountByLimit(limit);
    }

    @Override
    public Boolean addBlock(Block block) {
        if(block == null ||
                block.getGridId() == null ||
                block.getDrugId() == null ||
                block.getGridId() <= 0L ||
                block.getDrugId() <= 0L
        ) {
            throw new BlockOperateException(25002, "待添加的 Block 信息缺失");
        }
        return mapper.addBlock(block) > 0L;
    }

    @Override
    public Boolean removeBlock(Block block) {
        if(!isBlockInfOk(block)) {
            throw new BlockOperateException(25002, "待删除的 Block 信息缺失");
        }
        return mapper.removeById(block.getId()) > 0L;
    }

    @Override
    public Boolean depositDrug(Block block, Drug drug) {
        if(isDrugInfOk(drug)) {
            throw new BlockOperateException(25002, "药品信息缺失");
        }
        if(!isBlockInfOk(block)) {
            throw new BlockOperateException(25002, "Block 信息缺失");
        }
        if(!AttributeCheck.isIdOk(drug.getDrugInf().getDrugInfId())) {
            throw new BlockOperateException(25002, "药品信息缺失");
        }
        Grid grid = gridMapper.findById(block.getGridId());
        Long drugInfIdOfGrid = grid.getDrugInfId();
        if(drugInfIdOfGrid == null || drugInfIdOfGrid <= 0) {
            // 如果 grid 无药品信息，则说明 grid 为空，则为其绑定新的药品
            grid.setDrugInfId(drug.getDrugInf().getDrugInfId());
            Long modifyRes = gridMapper.modifyGrid(grid);
            if(modifyRes <= 0) {
                throw new GridOperateException(26002, "修改 Grid 信息失败");
            }
        } else if(!drugInfIdOfGrid.equals(drug.getDrugInf().getDrugInfId())) {
            throw new BlockOperateException(25002, "药品存放位置错误");
        } else {
            // 查询 Grid 中存储的
            Long stock = getStockInGrid(block.getGridId());
            Long capacity = Long.valueOf(gridMapper.findById(block.getGridId()).getCapacity());
            if(capacity <= stock) {
                throw new BlockOperateException(25002, "Grid 容量已达上限");
            }
        }
        block.setDrugId(drug.getId());
        if(mapper.modifyBlock(block) > 0) {
            throw new BlockOperateException(26002, "修改 Block 信息失败");
        }
        return true;
    }

    @Override
    public Boolean removeDrug(Block block) {
        if(!isBlockInfOk(block)) {
            throw new BlockOperateException(25002, "Block 信息缺失");
        }
        block.setDrugId(-1L);
        if(getStockInGrid(block.getGridId()) <= 0) {
            Grid grid = gridMapper.findById(block.getGridId());
            if(!isGridInfOk(grid)) {
                throw new BlockOperateException(25002, "Grid 信息缺失");
            }
            grid.setDrugInfId(-1L);
            gridMapper.modifyGrid(grid);
        }
        return null;
    }

    @Override
    public Drug getDrugInBlock(Block block) {
        if(block.getDrugId() == null || block.getDrugId() <= 0) {
            return null;
        }
        return drugMapper.findById(block.getDrugId());
    }

    @Override
    public Boolean isBlockEmpty(Long blockId) {
        Block block = mapper.findById(blockId);
        isBlockInfOk(block);
        return true;
    }

    private BlockVo setLimit(Drug drug) {
        if(!isDrugInfOk(drug)) {
            throw new BlockOperateException(25001, "药品信息缺失");
        }
        BlockVo limit = new BlockVo();
        limit.setDrugId(drug.getId());
        return limit;
    }

    private BlockVo setLimit(Grid grid) {
        if(!isGridInfOk(grid)) {
            throw new BlockOperateException(25001, "Grid 信息缺失");
        }
        BlockVo limit = new BlockVo();
        limit.setGridId(grid.getId());
        return limit;
    }

    private Long getStockInGrid(Long gridId) {
        BlockVo limit = new BlockVo();
        limit.setGridId(gridId);
        Long count = mapper.getCountByGridAndNotEmpty(limit);
        return count;
    }

    private Boolean isBlockInfOk(Block block) {
        if(block == null || block.getId() == null || block.getId() == 0l) {
            return false;
        }
        return true;
    }

    private Boolean isDrugInfOk(Drug drug) {
        if(drug == null || drug.getId() == null || drug.getId() == 0l) {
            return false;
        }
        return true;
    }

    private Boolean isGridInfOk(Grid grid) {
        if(grid == null || grid.getId() == null || grid.getId() == 0l) {
            return false;
        }
        return true;
    }
}

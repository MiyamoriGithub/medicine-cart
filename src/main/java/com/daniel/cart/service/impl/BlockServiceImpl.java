package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.result.ResultCodeEnum;
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
import java.util.Objects;

import static com.daniel.cart.util.AttributeCheck.isIdOk;
import static com.daniel.cart.util.AttributeCheck.isStringOk;

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

    // 规定 block 为空即药品信息为 -1
    private static final Long EMPTY = -1L;

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
    public List<Block> findAll(Integer start, Integer pageSize) {
        return findAllByLimit(null, null, null, null, null, null, start, pageSize);
    }

    @Override
    public List<Block> findByDrugInf(Long drugInfId) {
        return findByDrugInf(drugInfId, null, null);
    }

    @Override
    public List<Block> findByDrugInf(Long drugInfId, Integer start, Integer pageSize) {
        idCheck(drugInfId, "drugInfId");
        return findAllByLimit(null, drugInfId, null, null, null, null, start, pageSize);
    }

    @Override
    public List<Block> findByDrug(Long drugId) {
        return findByDrug(drugId, null, null);
    }

    @Override
    public List<Block> findByDrug(Long drugId, Integer start, Integer pageSize) {
        idCheck(drugId, "drugId");
        return findAllByLimit(drugId, null, null, null, null, null, start, pageSize);
    }

    @Override
    public List<Block> findByGrid(Grid grid) {
        return findByGrid(grid, null, null);
    }

    @Override
    public List<Block> findByGrid(Grid grid, Integer start, Integer pageSize) {
        if(grid.getId() != null) {
            return findByGrid(grid.getId(), start, pageSize);
        }
        // 如果 grid 中没有 id 信息，则通过位置信息查询 id 信息
        if(grid.getCartId() != null && grid.getLayer() != null && grid.getRow() != null && grid.getColumn() != null) {
            Long id = gridMapper.findByPosit(grid).getId();
            return findByGrid(id, start, pageSize);
        } else {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_QUERY_ERROR.getCode(), "grid 信息不足");
        }
    }

    @Override
    public List<Block> findByCart(Long cartId) {
        return findByCart(cartId, null, null);
    }

    @Override
    public List<Block> findByCart(Long cartId, Integer start, Integer pageSize) {
        idCheck(cartId, "cartId");
        return findAllByLimit(null, null, null, cartId, null, null, start, pageSize);

    }

    @Override
    public List<Block> findByLayer(Long cartId, Integer layer) {
        return findByLayer(cartId, layer, null, null);
    }

    @Override
    public List<Block> findByLayer(Long cartId, Integer layer, Integer start, Integer pageSize) {
        idCheck(cartId, "cartId");
        idCheck(layer, "layer");
        return findAllByLimit(null, null, null, cartId, layer, null, start, pageSize);
    }

    @Override
    public List<Block> findByDepart(Long departmentId) {
        return findByDepart(departmentId, null, null);
    }

    @Override
    public List<Block> findByDepart(Long departmentId, Integer start, Integer pageSize) {
        idCheck(departmentId, "departmentId");
        return findAllByLimit(null, null, null, null, null, departmentId, start, pageSize);
    }

    @Override
    public List<Block> findByGrid(Long gridId) {
        return findByGrid(gridId, null, null);
    }

    @Override
    public List<Block> findByGrid(Long gridId, Integer start, Integer pageSize) {
        idCheck(gridId, "gridId");
        return findAllByLimit(null, null, gridId, null, null,null, start, pageSize);
    }

    @Override
    public List<Block> findByLimit(BlockVo limit) {
        return mapper.findAllByLimit(limit);
    }

    @Override
    public Block findById(Long id) {
        idCheck(id, "id");
        return mapper.findById(id);
    }

    @Override
    public Block findByPosit(Grid grid, Integer serial) {
        idCheck(grid.getId(), "gridId");
        idCheck(serial, "serial");
        return mapper.findByPosit(grid.getId(), serial);
    }

    @Override
    public Long getCount() {
        return getCountByLimit(null, null, null, null, null);
    }

    @Override
    public Long getCountByDrug(Long drugId) {
        idCheck(drugId, "drugId");
        return getCountByLimit(drugId, null, null, null, null);
    }

    @Override
    public Long getCountByGrid(Grid grid) {
        return getCountByGrid(grid.getId());
    }

    public Long getCountByGrid(Long gridId) {
        idCheck(gridId, "gridId");
        return getCountByLimit(null, gridId, null, null, null);
    }

    @Override
    public Long getCountByCart(Long cartId) {
        idCheck(cartId, "cartId");
        return getCountByLimit(null, null, cartId, null, null);
    }

    @Override
    public Long getCountByLayer(Long cartId, Integer layer) {
        idCheck(cartId, "cartId");
        idCheck(layer, "layer");
        return getCountByLimit(null, null, cartId, layer, null);
    }

    @Override
    public Boolean add(Block block) {
        // 信息检查
        if(block == null ||
                block.getGridId() == null ||
                block.getDrugId() == null ||
                block.getGridId() <= 0L ||
                block.getDrugId() <= 0L
        ) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "待添加的 Block 信息缺失");
        }
        // 获取待添加 block 对应的 grid 对象
        Grid grid = gridMapper.findById(block.getGridId());
        Integer capacity = grid.getCapacity();
        // 获取当前 grid 对象对应的全部 block 对象
        List<Block> list = findByGrid(block.getGridId());
        // 当前 grid 已满
        if(capacity <= list.size()) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "待添加的 Grid 已满");
        }
        // 找到空缺的序号给到当前的 block
        boolean[] mark = new boolean[capacity];
        for (Block exist : list) {
            mark[exist.getSerial() - 1] = true;
        }
        for (int i = 0; i < mark.length; i++) {
            if(!mark[i]) {
                block.setSerial(i + 1);
                break;
            }
        }
        return mapper.addBlock(block) > 0L;
    }

    @Override
    public Boolean modify(Block block) {
        idCheck(block.getId(), "id");
        return mapper.modifyBlock(block) > 0;
    }

    @Override
    public Boolean remove(Long blockId) {
        idCheck(blockId, "blockId");
        return mapper.removeById(blockId) > 0L;
    }

    @Override
    public Boolean depositDrug(Block block, Drug drug) {
        isDrugInfOk(drug);
        isBlockInfOk(block);
        if(!AttributeCheck.isIdOk(drug.getDrugInf().getDrugInfId())) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "药品信息缺失");
        }
        if(block.getDrugId() != null && !Objects.equals(block.getDrugId(), EMPTY)) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "当前 block 已有药品");
        }
        // 如果药品库存不足
        if(drug.getStock() < 1) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "药品库存不足");
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
            // 如果从数据库中查到的 grid 绑定的药品信息和待存放药品的药品信息不一致，则说明药品存放位置错误
            throw new BlockOperateException(25002, "药品存放位置错误");
        } else {
            // 查询 Grid 中存储的药品数量并和 grid 的容量信息进行比较
            // 这里的判断逻辑好像没什么必要，因为只要 block 为空就肯定容量够用，但是代码能跑就先不改了
            Long stock = getStockInGrid(block.getGridId());
            Long capacity = Long.valueOf(gridMapper.findById(block.getGridId()).getCapacity());
            if(capacity <= stock) {
                throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "Grid 容量已达上限");
            }
        }
        // 修改 block 中的 drugId 信息并对数据库内容进行修改
        block.setDrugId(drug.getId());
        if(mapper.modifyBlock(block) <= 0) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "修改数据库 Block 中药品信息失败");
        }
        drug.setStock(drug.getStock() - 1);
        drugMapper.modifyDrug(drug);
        return true;
    }

    @Override
    public Boolean removeDrug(Long blockId) {
        // 检查 id 并通过 id 获取 block 对象
        idCheck(blockId, "blockId");
        Block block = mapper.findById(blockId);
        // 移除 block 中的药品信息
        block.setDrugId(EMPTY);
        Boolean res = mapper.modifyBlock(block) > 0;
        // 如果移除此药品导致 grid 为空，则移除 grid 绑定的药品信息
        if(getStockInGrid(block.getGridId()) <= 0) {
            Grid grid = gridMapper.findById(block.getGridId());
            isGridInfOk(grid);
            grid.setDrugInfId(EMPTY);
            gridMapper.modifyGrid(grid);
        }
        return res;
    }

    @Override
    public Drug getDrugInBlock(Long blockId) {
        Block block = mapper.findById(blockId);
        return drugMapper.findById(block.getDrugId());
    }

    @Override
    public Boolean isBlockEmpty(Long blockId) {
        Block block = mapper.findById(blockId);
        isBlockInfOk(block);
        return true;
    }

    private Long getCountByLimit(Long drugId, Long gridId, Long cartId, Integer layer, Long departmentId) {
        BlockVo limit = new BlockVo();
        limit.setDrugId(drugId);
        limit.setGridId(gridId);
        limit.setCartId(cartId);
        limit.setLayer(layer);
        limit.setDepartmentId(departmentId);
        return mapper.getCountByLimit(limit);
    }

    private List<Block> findAllByLimit(Long drugId, Long drugInfId, Long gridId, Long cartId, Integer layer, Long departmentId, Integer start, Integer pageSize) {
        BlockVo limit = new BlockVo();
        limit.setDrugId(drugId);
        limit.setDrugInfId(drugInfId);
        limit.setGridId(gridId);
        limit.setCartId(cartId);
        limit.setLayer(layer);
        limit.setDepartmentId(departmentId);
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findAllByLimit(limit);
    }

    private void idCheck(Long id, String name) {
        if(!isStringOk(name)) {
            name = "id";
        }
        if(!isIdOk(id)) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), name + "信息缺失");
        }
    }

    private void idCheck(Integer id, String name) {
        if(!isStringOk(name)) {
            name = "id";
        }
        if(!isIdOk(id)) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), name + "信息缺失");
        }
    }

    private Long getStockInGrid(Long gridId) {
        BlockVo limit = new BlockVo();
        limit.setGridId(gridId);
        return mapper.getCountByGridAndNotEmpty(limit);
    }

    private void isBlockInfOk(Block block) {
        if(block != null && AttributeCheck.isIdOk(block.getId())) {
            return;
        }
        throw new BlockOperateException(ResultCodeEnum.BLOCK_QUERY_ERROR.getCode(), "Block 信息缺失");
    }

    private void isDrugInfOk(Drug drug) {
        if(drug != null && AttributeCheck.isIdOk(drug.getId())) {
            return;
        }
        throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "drug 信息缺失");
    }

    private void isGridInfOk(Grid grid) {
        if(grid != null && AttributeCheck.isIdOk(grid.getId())) {
            return;
        }
        throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "grid 信息缺失");
    }
}

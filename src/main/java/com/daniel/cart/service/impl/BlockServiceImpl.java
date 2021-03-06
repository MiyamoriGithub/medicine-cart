package com.daniel.cart.service.impl;

import com.daniel.cart.domain.*;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.BlockVo;
import com.daniel.cart.exception.BlockOperateException;
import com.daniel.cart.exception.DrugOperateException;
import com.daniel.cart.exception.GridOperateException;
import com.daniel.cart.mapper.*;
import com.daniel.cart.service.BlockService;
import com.daniel.cart.util.AttributeCheck;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
    private final CartMapper cartMapper;
    private final EmployeeMapper employeeMapper;
    private final DrugOperateLogMapper drugOperateLogMapper;

    // 规定 block 为空即药品信息为 -1
    private static final Long EMPTY = -1L;

    public BlockServiceImpl(BlockMapper mapper, GridMapper gridMapper, DrugMapper drugMapper, CartMapper cartMapper, EmployeeMapper employeeMapper, DrugOperateLogMapper drugOperateLogMapper) {
        this.mapper = mapper;
        this.gridMapper = gridMapper;
        this.drugMapper = drugMapper;
        this.cartMapper = cartMapper;
        this.employeeMapper = employeeMapper;
        this.drugOperateLogMapper = drugOperateLogMapper;
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
    public Block findByPosit(Long cartId, Integer layer, Integer row, Integer column, Integer serial) {
        return mapper.findByPosit2(cartId, layer, row, column, serial);
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
        if(drug.getStock() < 1) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "药品库存不足");
        }

        Grid grid = gridMapper.findById(block.getGridId());
        Cart cart = cartMapper.getById(grid.getCartId());
        if(cart.getState() == CartStateEnum.free) {
            throw new DrugOperateException("急救车为空闲状态，无法存入药品");
        }
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
        }

        // 存入之前查询容量，如果存入之后已满就修改 grid 表值
        Long stock = getStockInGrid(block.getGridId());
        Grid g = gridMapper.findById(block.getGridId());
        Long capacity = Long.valueOf(g.getCapacity());
        if(capacity == stock + 1) {
            g.setIsFull(true);
            gridMapper.modifyGrid(g);
        }

        // 修改 block 中的 drugId 信息并对数据库内容进行修改
        block.setDrugId(drug.getId());
        if(mapper.modifyBlock(block) <= 0) {
            throw new BlockOperateException(ResultCodeEnum.BLOCK_OPERATE_ERROR.getCode(), "修改数据库 Block 中药品信息失败");
        }
        drug.setStock(drug.getStock() - 1);
        drugMapper.modifyDrug(drug);

        // 写 log
        writeLog(drug, block.getId(), g.getCartId(), "in");
        return true;
    }

    @Override
    public Boolean removeDrug(Long blockId) {
        // 检查 id 并通过 id 获取 block 对象
        idCheck(blockId, "blockId");
        Block block = mapper.findById(blockId);
        return removeDrug(block);
    }

    public Boolean removeDrug(Block block) {
        // 写 log 用的，没啥实质性意义
        Drug toRemove = new Drug();
        toRemove.setId(block.getDrugId());

        // 避免重复取出药品
        if(EMPTY.equals(block.getDrugId())) {
            throw new DrugOperateException("Block 为空，没有可被取出的药品");
        }
        Grid grid = gridMapper.findById(block.getGridId());
        Cart cart = cartMapper.getById(grid.getCartId());
        if(cart.getState() == CartStateEnum.free) {
            throw new DrugOperateException("急救车为空闲状态，无法取出药品");
        }

        // 移除 block 中的药品信息
        block.setDrugId(EMPTY);
        Boolean res = mapper.modifyBlock(block) > 0;

        // 如果 grid 之前为满，则移除当前 block 药品后不为满
        if(grid.getIsFull()) {
            grid.setIsFull(false);
            gridMapper.modifyGrid(grid);
        } else if(getStockInGrid(grid.getId()) <= 0) {
            // 如果移除此药品导致 grid 为空，则移除 grid 绑定的药品信息
            isGridInfOk(grid);
            grid.setDrugInfId(EMPTY);
            gridMapper.modifyGrid(grid);
        }

        // 写 log
        writeLog(toRemove, block.getId(), grid.getCartId(), "out");
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
        return AttributeCheck.isIdOk(block.getDrugId());
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

    private void writeLog(Drug drug, Long blockId, Long cartId, String operateType) {
        DrugOperateLog log = new DrugOperateLog();
        Cart cart = cartMapper.getById(cartId);
        log.setDrug(drug);
        log.setBlockId(blockId);
        log.setCartId(cartId);
        log.setOperateType(operateType);
        log.setState(cart.getState());
        Subject subject = SecurityUtils.getSubject();
        String principal = (String)subject.getPrincipal();
        Employee employee = employeeMapper.findByPhone(principal);
        if(employee != null && employee.getId() != null) {
            log.setEmployeeId(employee.getId());
        } else {
            log.setEmployeeId(2L);
        }
        drugOperateLogMapper.add(log);
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

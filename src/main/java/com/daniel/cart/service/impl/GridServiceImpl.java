package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.BlockVo;
import com.daniel.cart.domain.vo.GridVo;
import com.daniel.cart.exception.GridOperateException;
import com.daniel.cart.mapper.BlockMapper;
import com.daniel.cart.mapper.GridMapper;
import com.daniel.cart.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.daniel.cart.util.AttributeCheck.isIdOk;

/**
 * GridService 接口的实现类
 *
 * @author Daniel Zheng
 **/

@Service("gridService")
@Transactional
public class GridServiceImpl implements GridService {

    private final GridMapper mapper;
    private final BlockMapper blockMapper;

    @Autowired
    public GridServiceImpl(GridMapper mapper, BlockMapper blockMapper) {
        this.mapper = mapper;
        this.blockMapper = blockMapper;
    }

    @Override
    public List<Grid> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Grid> findAll(Integer start, Integer pageSize) {
        return findByLimit(null, null, start, pageSize);
    }

//    @Override
//    public List<Grid> findByDepartment() {
//        return null;
//    }

    @Override
    public List<Grid> findByCart(Long cartId) {
        return findByCart(cartId, null, null);
    }

    @Override
    public List<Grid> findByCart(Long cartId, Integer start, Integer pageSize) {
        if(!isIdOk(cartId)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(),  "cart id 信息缺失");
        }
        return findByLimit(cartId, null, start, pageSize);
    }

    @Override
    public List<Grid> findByLayer(Long cartId, Integer layer) {
        return findByLayer(cartId, layer, null, null);
    }

    @Override
    public List<Grid> findByLayer(Long cartId, Integer layer, Integer start, Integer pageSize) {
        if(!isIdOk(cartId)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), "cart id 信息缺失");
        }
        if(!isIdOk(layer)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), "layer 信息缺失");
        }
        return findByLimit(cartId, layer, start, pageSize);
    }

    @Override
    public List<Grid> findNeedInventory() {
        List<Grid> grids = findAll();
        return findNeedInventory(grids);
    }

    @Override
    public List<Grid> findNeedInventory(List<Grid> grids) {
        List<Grid> res = new ArrayList<>();
        for (Grid grid : grids) {
            BlockVo limit = new BlockVo();
            limit.setGridId(grid.getId());
            Long count = blockMapper.getCountByGridAndNotEmpty(limit);
            if(count < grid.getCapacity()) {
                res.add(grid);
            }
        }
        return res;
    }

    @Override
    public Grid findById(Long id) {
        if(!isIdOk(id)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(),  "id 信息缺失");
        }
        return mapper.findById(id);
    }

    @Override
    public Grid findByPosit(Long cartId, Integer layer, Integer row, Integer column) {
        if(!isIdOk(cartId) || !isIdOk(layer) || !isIdOk(row) || !isIdOk(column)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), "位置信息缺失");
        }
        Grid grid = new Grid();
        grid.setCartId(cartId);
        grid.setLayer(layer);
        grid.setRow(row);
        grid.setColumn(column);
        return mapper.findByPosit(grid);
    }

//    @Override
//    public List<Block> findByGridId(Long id) {
//        if(isIdOk(id)) {
//            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(),  "id 信息缺失");
//        }
//        BlockVo limit = new BlockVo();
//        limit.setGridId(id);
//        return blockMapper.findAllByLimit(limit);
//    }

    @Override
    public Long getCount() {
        return mapper.getCount();
    }

    @Override
    public Long getCountByCart(Long cartId) {
        if(!isIdOk(cartId)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), "cart id 信息缺失");
        }
        return getCountByLimit(cartId, null);
    }

    @Override
    public Long getCountByLayer(Long cartId, Integer layer) {
        if(!isIdOk(cartId)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), "cart id 信息缺失");
        }
        if(!isIdOk(layer)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), "layer 信息缺失");
        }
        return getCountByLimit(cartId, layer);
    }

    @Override
    public Boolean addGrid(Grid grid) {
        grid.setId(null);
        if(findByPosit(grid.getCartId(), grid.getLayer(), grid.getRow(), grid.getColumn()) != null) {
            throw new GridOperateException(ResultCodeEnum.GRID_OPERATE_ERROR.getCode(), "待添加的 Grid 已经存在");
        }
        return mapper.addGrid(grid) > 0;
    }

    @Override
    public Boolean modifyDrugInfInGrid(Grid grid) {
        if(findById(grid.getId()) == null) {
            throw new GridOperateException(ResultCodeEnum.GRID_OPERATE_ERROR.getCode(), "待修改的 Grid 不存在");
        }
        if(findByPosit(grid.getCartId(), grid.getLayer(), grid.getRow(), grid.getColumn()) == null) {
            throw new GridOperateException(ResultCodeEnum.GRID_OPERATE_ERROR.getCode(), "待修改的 Grid 不存在");
        }
        return mapper.modifyGrid(grid) > 0;
    }

    @Override
    public Boolean removeGrid(Long id) {
        if(findById(id) == null) {
            throw new GridOperateException(ResultCodeEnum.GRID_OPERATE_ERROR.getCode(), "待修改的 Grid 不存在");
        }
        return mapper.removeById(id) > 0;
    }

    private List<Grid> findByLimit(Long cartId, Integer layer, Integer start, Integer pageSize) {
        GridVo limit = new GridVo();
        limit.setCartId(cartId);
        limit.setLayer(layer);
        if(start != null && start > 0) {
            limit.setStart(start);
        }
        if(pageSize != null && pageSize > 0) {
            limit.setPageSize(pageSize);
        }
        return mapper.findAllByLimit(limit);
    }

    private Long getCountByLimit(Long cartId, Integer layer) {
        GridVo limit = new GridVo();
        limit.setCartId(cartId);
        limit.setLayer(layer);
        return mapper.getCountByLimit(limit);
    }

}

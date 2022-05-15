package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.enums.DrugExceptionEnum;
import com.daniel.cart.domain.res.GridDrugRes;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.BlockVo;
import com.daniel.cart.domain.vo.GridVo;
import com.daniel.cart.exception.GridOperateException;
import com.daniel.cart.mapper.BlockMapper;
import com.daniel.cart.mapper.GridMapper;
import com.daniel.cart.service.GridService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.daniel.cart.util.AttributeCheck.isIdOk;
import static com.daniel.cart.util.AttributeCheck.isStringOk;

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

    // 规定 block 为空即药品信息为 -1
    private static final Long EMPTY = -1L;

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

    @Override
    public List<GridDrugRes> findByCart2(Long cartId) {
        idCheck(cartId, "cartID");
        return mapper.findByCart(cartId);
    }

    @Override
    public List<Grid> findByCart(Long cartId) {
        return findByCart(cartId, null, null);
    }

    @Override
    public List<Grid> findByCart(Long cartId, Integer start, Integer pageSize) {
        idCheck(cartId, "cartId");
        return findByLimit(cartId, null, start, pageSize);
    }

    @Override
    public List<Grid> findByLayer(Long cartId, Integer layer) {
        return findByLayer(cartId, layer, null, null);
    }

    @Override
    public List<Grid> findByLayer(Long cartId, Integer layer, Integer start, Integer pageSize) {
        idCheck(cartId, "cartId");
        idCheck(layer, "layer");
        return findByLimit(cartId, layer, start, pageSize);
    }

    @Override
    public List<GridDrugRes> findException(List<GridDrugRes> grids, Map<DrugExceptionEnum, HashSet<Long>> map) {
        for(GridDrugRes grid : grids) {
            List<String> exceptions = new ArrayList<String>();
            for (Drug drug : grid.getDrugs()) {
                for (DrugExceptionEnum drugExceptionEnum : map.keySet()) {
                    HashSet<Long> drugIds = map.get(drugExceptionEnum);
                    if(drugIds.contains(drug.getId())) {
                        exceptions.add(drugExceptionEnum.getName());
                    }
                }
            }
            if(!grid.getIsFull()) {
                exceptions.add(DrugExceptionEnum.vacant.getName());
            }
            grid.setExceptions(exceptions);
        }
        return grids;
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
        idCheck(id, "id");
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

    @Override
    public Long getCount() {
        return mapper.getCount();
    }

    @Override
    public Long getCountByCart(Long cartId) {
        idCheck(cartId, "cartId");
        return getCountByLimit(cartId, null);
    }

    @Override
    public Long getCountByLayer(Long cartId, Integer layer) {
        idCheck(cartId, "cartId");
        idCheck(layer, "layer");
        return getCountByLimit(cartId, layer);
    }

    @Override
    public Boolean add(Grid grid) {
        grid.setId(null);
        if(findByPosit(grid.getCartId(), grid.getLayer(), grid.getRow(), grid.getColumn()) != null) {
            throw new GridOperateException(ResultCodeEnum.GRID_OPERATE_ERROR.getCode(), "待添加的 Grid 已经存在");
        }
        return mapper.addGrid(grid) > 0;
    }

    @Override
    public Boolean modify(Grid grid) {
        // 如果通过 id 和 位置信息都无法找在数据库到 grid 信息
        if(findById(grid.getId()) == null && findByPosit(grid.getCartId(), grid.getLayer(), grid.getRow(), grid.getColumn()) == null) {
            throw new GridOperateException(ResultCodeEnum.GRID_OPERATE_ERROR.getCode(), "待修改的 Grid 不存在");
        }
        return mapper.modifyGrid(grid) > 0;
    }

    @Override
    public Boolean remove(Long id) {
        idCheck(id, "id");
        return mapper.removeById(id) > 0;
    }

    private List<Grid> findByLimit(Long cartId, Integer layer, Integer start, Integer pageSize) {
        GridVo limit = new GridVo();
        limit.setCartId(cartId);
        limit.setLayer(layer);
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findAllByLimit(limit);
    }

    private Long getCountByLimit(Long cartId, Integer layer) {
        GridVo limit = new GridVo();
        limit.setCartId(cartId);
        limit.setLayer(layer);
        return mapper.getCountByLimit(limit);
    }


    private void idCheck(Long id, String name) {
        if(!isStringOk(name)) {
            name = "id";
        }
        if(!isIdOk(id)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), name + "信息缺失");
        }
    }

    private void idCheck(Integer id, String name) {
        if(!isStringOk(name)) {
            name = "id";
        }
        if(!isIdOk(id)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), name + "信息缺失");
        }
    }

}

package com.daniel.cart.service;

import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.enums.DrugExceptionEnum;
import com.daniel.cart.domain.res.GridDrugRes;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Grid 实体类的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
public interface GridService extends AbstractService<Grid> {

    List<GridDrugRes> findByCart2(Long cartId);

    List<Grid> findByCart(Long cartId);

    List<Grid> findByCart(Long cartId, Integer start, Integer pageSize);

    List<Grid> findByLayer(Long cartId, Integer layer);

    List<Grid> findByLayer(Long cartId, Integer layer, Integer start, Integer pageSize);

    List<GridDrugRes> findException(List<GridDrugRes> grids, Map<DrugExceptionEnum, HashSet<Long>> map);

    List<Grid> findNeedInventory();

    List<Grid> findNeedInventory(List<Grid> grids);

    Grid findByPosit(Long cartId, Integer layer, Integer row, Integer column);

    Long getCountByCart(Long cartId);

    Long getCountByLayer(Long cartId, Integer layer);
}

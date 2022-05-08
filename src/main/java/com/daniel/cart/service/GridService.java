package com.daniel.cart.service;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Grid;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Grid 实体类的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
public interface GridService {

    List<Grid> findAll();

    List<Grid> findAll(Integer start, Integer pageSize);

//    List<Grid> findByDepartment();

    List<Grid> findByCart(Long cartId);

    List<Grid> findByCart(Long cartId, Integer start, Integer pageSize);

    List<Grid> findByLayer(Long cartId, Integer layer);

    List<Grid> findByLayer(Long cartId, Integer layer, Integer start, Integer pageSize);

    List<Grid> findNeedInventory();

    List<Grid> findNeedInventory(List<Grid> grids);

    Grid findById(Long id);

    Grid findByPosit(Long cartId, Integer layer, Integer row, Integer column);

//    List<Block> findByGridId(Long id);

    Long getCount();

    Long getCountByCart(Long cartId);

    Long getCountByLayer(Long cartId, Integer layer);

    Boolean addGrid(Grid grid);

    Boolean modifyDrugInfInGrid(Grid grid);

    Boolean removeGrid(Long id);
}

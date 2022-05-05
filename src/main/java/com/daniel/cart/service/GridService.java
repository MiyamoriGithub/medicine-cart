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

    List<Grid> findAllByCart(Long cartId);

    List<Grid> findAllByCart(Long cartId, Integer start, Integer pageSize);

    List<Grid> findAllByCartAndLayer(Long cartId, Integer layer);

    List<Grid> findAllByCartAndLayer(Long cartId, Integer layer, Integer start, Integer pageSize);

    Grid findById(Long id);

    Grid findByPosit(Grid grid);

    /**
     * 查询 Grid id 对应的所有 Block
     * @param id Grid Id
     * @return 满足条件的所有 Block 类对象
     */
    List<Block> findBlocksByGridId(Long id);

    Long getCountByCart(Long cartId);

    Long getCountByCartAndLayer(Long cartId, Integer layer);

    Long addGrid(Grid grid);

    Long modifyDrugInfInGrid(Grid grid);

    Long removeGrid(Long id);
}

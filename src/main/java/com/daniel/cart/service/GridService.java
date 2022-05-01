package com.daniel.cart.service;

import com.daniel.cart.domain.Grid;
import java.util.List;

/**
 * Grid 实体类的 Service 层接口
 *
 * @author Daniel Zheng
 **/

public interface GridService {

    List<Grid> findAll();

    List<Grid> findAllByCart(Long cartId);

    List<Grid> findAllByCart(Long cartId, Integer start, Integer pageSize);

    List<Grid> findAllByCartAndLayer(Long cartId, Integer layer);

    List<Grid> findAllByCartAndLayer(Long cartId, Integer layer, Integer start, Integer pageSize);

    Grid findById(Long id);

    Long getCountByCart(Long cartId);

    Long getCountByCartAndLayer(Long cartId, Integer layer);
}

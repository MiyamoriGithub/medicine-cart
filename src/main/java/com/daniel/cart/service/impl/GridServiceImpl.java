package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Grid;
import com.daniel.cart.mapper.GridMapper;
import com.daniel.cart.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GridService 接口的实现类
 *
 * @author Daniel Zheng
 * @create 2022-05-01 14:49
 **/

@Service("gridService")
@Transactional
public class GridServiceImpl implements GridService {

    private final GridMapper mapper;

    @Autowired
    public GridServiceImpl(GridMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Grid> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Grid> findAllByCart(Long cartId) {
        return null;
    }

    @Override
    public List<Grid> findAllByCart(Long cartId, Integer start, Integer pageSize) {
        return null;
    }

    @Override
    public List<Grid> findAllByCartAndLayer(Long cartId, Integer layer) {
        return null;
    }

    @Override
    public List<Grid> findAllByCartAndLayer(Long cartId, Integer layer, Integer start, Integer pageSize) {
        return null;
    }

    @Override
    public Grid findById(Long id) {
        return null;
    }

    @Override
    public Long getCountByCart(Long cartId) {
        return null;
    }

    @Override
    public Long getCountByCartAndLayer(Long cartId, Integer layer) {
        return null;
    }
}

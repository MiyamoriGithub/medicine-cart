package com.daniel.cart.mapper;

import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.vo.GridVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Grid 实体类的 Mapper 接口
 *
 * @author Daniel Zheng
 **/

@Mapper
@Repository
public interface GridMapper {
    List<Grid> findAll();

    List<Grid> findAllByLimit(GridVo limit);

    Grid findById(Long id);

    Grid findByPosit(Grid grid);

    Long getCount();

    Long getCountByLimit(GridVo limit);

    Long addGrid(Grid grid);

    Long removeById(Long id);

    Long modifyGrid(Grid grid);
}

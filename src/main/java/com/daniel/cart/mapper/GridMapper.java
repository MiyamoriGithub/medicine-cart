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
    public List<Grid> findAll();

    public List<Grid> findAllByLimit(GridVo limit);

    public Grid findById(Long id);

    public Long getCountByLimit(GridVo limit);

    public Long addGrid(Grid grid);

    public Long removeById(Long id);

    public Long modifyGrid(Grid grid);
}

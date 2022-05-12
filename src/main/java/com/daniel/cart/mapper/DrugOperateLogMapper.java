package com.daniel.cart.mapper;

import com.daniel.cart.domain.CartOperateLog;
import com.daniel.cart.domain.DrugOperateLog;
import com.daniel.cart.domain.vo.PageVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 药品操作日志 mapper
 *
 * @author Daniel Zheng
 **/

@Mapper
@Repository
public interface DrugOperateLogMapper {

    List<DrugOperateLog> findAll();

    List<DrugOperateLog> findByLimit(PageVo limit);

    Long getCount();

    Long add(DrugOperateLog drugOperateLog);
}

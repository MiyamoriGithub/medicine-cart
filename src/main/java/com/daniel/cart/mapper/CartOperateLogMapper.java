package com.daniel.cart.mapper;

import com.daniel.cart.domain.CartOperateLog;
import com.daniel.cart.domain.vo.PageVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 急救车操作日志实体类
 *
 * @author Daniel Zheng
 **/

@Mapper
@Component
public interface CartOperateLogMapper {

    List<CartOperateLog> findAll();

    List<CartOperateLog> findByLimit(PageVo limit);

    Long getCount();

    Long add(CartOperateLog cartOperateLog);

}

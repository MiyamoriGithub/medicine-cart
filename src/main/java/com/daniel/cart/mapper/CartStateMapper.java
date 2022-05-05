package com.daniel.cart.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 急救车状态的 Mapper 层接口，我何苦呢，直接打印CartStateEnum不就完事了
 *
 * @author Daniel Zheng
 * @create 2022-05-02 18:20
 **/

@Mapper
@Repository
public interface CartStateMapper {
    @MapKey(value = "state")
    List<Map<String, String>> findAll();
}

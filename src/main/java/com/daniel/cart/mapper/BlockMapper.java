package com.daniel.cart.mapper;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.vo.BlockVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 基本功能测试无误

@Mapper
@Repository
public interface BlockMapper {
    List<Block> findAll();

    List<Block> findAllByLimit(BlockVo limit);

    List<Block> findByCart(Long cartId);

    Block findById(Long id);

    Block findByPosit(Long gridId, Integer serial);

    Block findByPosit2(Long cartId, Integer layer, Integer row, Integer column, Integer serial);

    Long getCountByLimit(BlockVo limit);

    Long getCountByGridAndNotEmpty(BlockVo limit);

    Long addBlock(Block block);

    Long removeById(Long id);

    Long modifyBlock(Block block);
}

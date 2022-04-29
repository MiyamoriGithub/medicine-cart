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
    public List<Block> findAll();

    public List<Block> findAllByLimit(BlockVo limit);

    public Block findById(Long id);

    public Long getCountByLimit(BlockVo limit);

    public Long addBlock(Block block);

    public Long removeById(Long id);

    public Long modifyBlock(Block block);
}

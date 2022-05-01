package com.daniel.cart.service;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.Grid;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Block 实体类的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
public interface BlockService {

    List<Block> findAll();

    List<Block> findAllByDrug(Drug drug);

    List<Block> findAllByDrug(Long drugId);

    List<Block> findAllByGrid(Grid grid);

    List<Block> findAllByGrid(Long gridId);

    Block findById(Long id);

    Long getCountByDrug(Drug drug);

    Long getCountByDrug(Long drugId);

    Long getCountByGrid(Grid grid);

    Long getCountByGrid(Long gridId);

    Boolean addBlock(Block block);

    Boolean removeBlock(Block block);

    /**
     * 向 block 中存入 drug
     * @param block
     * @param drug
     * @return 是否成功存入
     */
    Boolean depositDrug(Block block, Drug drug);

    /**
     * 从 block 中取出 drug
     * @param block
     * @return 是否成功取出
     */
    Boolean removeDrug(Block block);

    /**
     * 获取 block 中的药品实体类
     * @param block
     * @return 对应药品实体类
     */
    Drug getDrugInBlock(Block block);

    /**
     * 判断 block 是否为空
     * @param blockId
     * @return 是否为空
     */
    Boolean isBlockEmpty(Long blockId);
}

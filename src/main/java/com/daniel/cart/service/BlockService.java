package com.daniel.cart.service;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.vo.BlockVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Block 实体类的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
public interface BlockService extends AbstractService<Block> {

    List<Block> findByDrugInf(Long drugInfId);

    List<Block> findByDrugInf(Long drugInfId, Integer start, Integer pageSize);

    List<Block> findByDrug(Long drugId);

    List<Block> findByDrug(Long drugId, Integer start, Integer pageSize);

    List<Block> findByGrid(Grid grid);

    List<Block> findByGrid(Grid grid, Integer start, Integer pageSize);

    List<Block> findByGrid(Long gridId);

    List<Block> findByGrid(Long gridId, Integer start, Integer pageSize);

    List<Block> findByCart(Long cartId);

    List<Block> findByCart(Long cartId, Integer start, Integer pageSize);

    List<Block> findByLayer(Long cartId, Integer layer);

    List<Block> findByLayer(Long cartId, Integer layer, Integer start, Integer pageSize);

    List<Block> findByDepart(Long departmentId);

    List<Block> findByDepart(Long departmentId, Integer start, Integer pageSize);
    /**
     * 根据限制条件查询 Block
     * 备用方法，尽量不要使用
     * @param limit 限制条件
     * @return Block List
     */
    List<Block> findByLimit(BlockVo limit);

    Block findByPosit(Grid grid, Integer serial);

    Long getCountByDrug(Long drugId);

    Long getCountByGrid(Grid grid);

    Long getCountByGrid(Long gridId);

    Long getCountByCart(Long cartId);

    Long getCountByLayer(Long cartId, Integer layer);

    /**
     * 向 block 中存入 drug
     * @param block block
     * @param drug drug
     * @return 是否成功存入
     */
    Boolean depositDrug(Block block, Drug drug);

    /**
     * 从 block 中取出 drug
     * @param blockId blockId
     * @return 是否成功取出
     */
    Boolean removeDrug(Long blockId);

    /**
     * 获取 block 中的药品实体类
     * @param blockId blockId
     * @return 对应药品实体类
     */
    Drug getDrugInBlock(Long blockId);

    /**
     * 判断 block 是否为空
     * @param blockId blockId
     * @return 是否为空
     */
    Boolean isBlockEmpty(Long blockId);


}

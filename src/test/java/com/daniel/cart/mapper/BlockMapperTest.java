package com.daniel.cart.mapper;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.vo.BlockVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BlockMapperTest {

    @Autowired
    private BlockMapper mapper;

    @Test
    public void findAll() {
        for (Block block : mapper.findAll()) {
            System.out.println(block);
        }
    }

    @Test
    public void findAllByLimit() {
        BlockVo limit = new BlockVo();
//        limit.setGridId(1L);
//        limit.setPageSize(5);
        limit.setCartId(1L);
        limit.setLayer(1);
//        limit.setDepartmentId(1L);
        for (Block block : mapper.findAllByLimit(limit)) {
            System.out.println(block);
        }
    }

    @Test
    public void findById() {
        System.out.println(mapper.findById(10L));
    }

    @Test
    public void getCountByLimit() {
        BlockVo limit = new BlockVo();
        limit.setGridId(1L);
        limit.setPageSize(5);
        System.out.println(mapper.getCountByLimit(limit));
    }

    @Test
    public void addBlock() {
        Block block = new Block();
        block.setGridId(4L);
//        block.setDrugId(6l);
        System.out.println(mapper.addBlock(block));
    }

    @Test
    public void removeById() {
        System.out.println(mapper.removeById(25L));
    }

    @Test
    public void modifyBlock() {
        Block block = mapper.findById(25L);
        block.setDrugId(6L);
        System.out.println(mapper.modifyBlock(block));
    }
}
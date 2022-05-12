package com.daniel.cart.mapper;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.DrugOperateLog;
import com.daniel.cart.domain.Grid;
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
public class DrugOperateLogMapperTest {

    @Autowired
    public DrugOperateLogMapper mapper;
    @Autowired
    public DrugMapper drugMapper;
    @Autowired
    public BlockMapper blockMapper;
    @Autowired
    public GridMapper gridMapper;

    @Test
    public void findAll() {
        for (DrugOperateLog drugOperateLog : mapper.findAll()) {
            System.out.println(drugOperateLog.toString());
        }
    }

    @Test
    public void findByLimit() {

    }

    @Test
    public void getCount() {
        System.out.println(mapper.getCount());
    }

    @Test
    public void add() {
        Drug drug = drugMapper.findById(1L);
        Block block = blockMapper.findById(1L);
        Grid grid = gridMapper.findById(block.getGridId());
        DrugOperateLog log = new DrugOperateLog();
        log.setDrug(drug);
        log.setBlockId(block.getId());
        log.setCartId(grid.getCartId());
        log.setOperateType("in");
        log.setEmployeeId(2L);
        mapper.add(log);
    }
}
package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Block;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.service.BlockService;
import com.daniel.cart.service.DrugService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BlockServiceImplTest {
    @Autowired
    private BlockService service;
    @Autowired
    private DrugService drugService;

    @Test
    public void addBlock() {
        List<Block> blocks = service.findAll();
        for(Block block : blocks) {
//            System.out.println(block.toString());
            long id = block.getGridId() + 111;
            if(id > 147) {
                break;
            }
            block.setGridId(id);
            service.add(block);
        }
    }

    @Test
    public void depositDrug() {
        Block block = service.findById(147L);
        Drug drug = drugService.findById(30L);
//        service.depositDrug(block, drug);
    }

    @Test
    public void getCount() {
        System.out.println(service.getCount());
    }

}
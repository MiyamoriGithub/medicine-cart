package com.daniel.cart.service;

import com.daniel.cart.domain.Grid;
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
public class GridServiceTest {

    @Autowired
    GridService service;

    @Test
    public void modify() {
//        List<Grid> grids = service.findAll();
//        for (Grid grid : grids) {
//            if(grid.getId() > 54) {
//                grid.setId(grid.getId() - 3);
//            }
//            service.modify(grid);
//        }
    }

}
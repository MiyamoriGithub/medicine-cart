package com.daniel.cart.mapper;

import com.daniel.cart.domain.Grid;
import com.daniel.cart.domain.res.GridDrugRes;
import com.daniel.cart.domain.vo.GridVo;
import com.daniel.cart.mapper.GridMapper;
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
public class GridMapperTest {

    @Autowired
    private GridMapper mapper;

    @Test
    public void findAll() {
        for (Grid grid : mapper.findAll()) {
            System.out.println(grid);
        }
    }

    @Test
    public void findAllByLimit() {
        GridVo limit = new GridVo();
        limit.setDrugInfId(1L);
        for (Grid grid : mapper.findAllByLimit(limit)) {
            System.out.println(grid);
        }
    }

    @Test
    public void findByCart() {
        for (GridDrugRes gridDrugRes : mapper.findByCart(1L)) {
            System.out.println(gridDrugRes);
        }
    }

    @Test
    public void findById() {
        System.out.println(mapper.findById(3L));
    }

    @Test
    public void getCountByLimit() {
        GridVo limit = new GridVo();
        limit.setCartId(1L);
        System.out.println(mapper.getCountByLimit(limit));
    }

    @Test
    public void addGrid() {
        Grid grid = new Grid();
        grid.setCartId(4L);
        grid.setLayer(1);
        grid.setRow(1);
        grid.setColumn(1);
        System.out.println(mapper.addGrid(grid));
    }

    @Test
    public void removeById() {
        System.out.println(mapper.removeById(56L));
    }

    @Test
    public void modifyGrid() {
        Grid grid = mapper.findById(56L);
        grid.setDrugInfId(1L);
        System.out.println(mapper.modifyGrid(grid));
    }
}
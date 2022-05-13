package com.daniel.cart.mapper;

import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.res.DrugInfRes;
import com.daniel.cart.domain.vo.DrugInfVo;
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
public class DrugInfMapperTest {

    @Autowired
    DrugInfMapper mapper;

    @Test
    public void findAll() {
        for (DrugInf drugInf : mapper.findAll()) {
            System.out.println(drugInf.toString());
        }
    }

    @Test
    public void findByLimit() {
        DrugInfVo limit = new DrugInfVo();
        limit.setStart(1);
        limit.setPageSize(10);
        limit.setNameCondition("注射");
        for (DrugInf drugInf : mapper.findByLimit(limit)) {
            System.out.println(drugInf);
        }
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByBarcode() {
    }

    @Test
    public void getCount() {
    }

    @Test
    public void getCountByLimit() {
    }

    @Test
    public void addDrugInf() {
    }

    @Test
    public void modifyDrugInf() {
    }

    @Test
    public void removeDrugInf() {
    }

    @Test
    public void findWithDrugList() {
        for (DrugInfRes drugInfRes : mapper.findWithDrugList()) {
            System.out.println(drugInfRes.toString());
        }

    }
}
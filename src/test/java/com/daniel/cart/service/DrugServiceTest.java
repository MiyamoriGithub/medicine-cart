package com.daniel.cart.service;

import com.daniel.cart.domain.Drug;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DrugServiceTest {

    @Autowired
    private DrugService service;

    @Test
    public void findAll() {
        for (Drug drug : service.findAll()) {
            System.out.println(drug.toString());
        }
    }

    @Test
    public void findAllTemporary() {
        for (Drug drug : service.findTemporary()) {
            System.out.println(drug);
        }
    }

    @Test
    public void findAllExpire() {
        for (Drug drug : service.findExpire()) {
            System.out.println(drug);
        }
    }

    @Test
    public void findByBarcode() {
    }

    @Test
    public void findByLimit() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void getCountByLimit() {
    }

    @Test
    public void addDrug() {
    }

    @Test
    public void modifyDrug() {
    }

    @Test
    public void deleteDrug() {
    }
}
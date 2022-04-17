package com.daniel.cart.controller;

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
public class CartControllerTest {

    @Autowired
    CartController controller;

    @Test
    public void findAll() {
    }

    @Test
    public void findById() {
        System.out.println(controller.findById(1l).toString());
    }
}
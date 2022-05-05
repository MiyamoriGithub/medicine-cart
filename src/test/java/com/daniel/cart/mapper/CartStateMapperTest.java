package com.daniel.cart.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CartStateMapperTest {

    @Autowired
    CartStateMapper mapper;
    
    @Test
    public void findAll() {
        List<Map<String, String>> list = mapper.findAll();
        for (Map<String, String> map : list) {
            System.out.println(map);
        }
    }
}
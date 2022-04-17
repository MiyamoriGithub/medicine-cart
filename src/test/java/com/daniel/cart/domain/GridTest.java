package com.daniel.cart.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GridTest {
    @Test
    public void test() {
        Grid a = new Grid(1l,2,3,4);
        a.setDrugName("time");
        Grid b = new Grid(1l,2,3,4);
        b.setDrugName("hour");
        System.out.println(a.equals(b));
    }
}

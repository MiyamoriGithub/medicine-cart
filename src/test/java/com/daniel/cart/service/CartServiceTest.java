package com.daniel.cart.service;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CartServiceTest {

    @Autowired
    private CartService service;

    @Test
    public void findAll() {
    }

    @Test
    public void findAllByDepartment() {
    }

    @Test
    public void testFindAllByDepartment() {
    }

    @Test
    public void findAllByState() {
        List<Cart> carts = service.findAllByState("inventory");
        for (Cart cart : carts) {
            System.out.println(cart);
        }
    }

    @Test
    public void findById() {
    }

    @Test
    public void getCountByState() {
        System.out.println(service.getCountByState("free"));
    }

    @Test
    public void getCountByDepartment() {
    }

    @Test
    public void testGetCountByDepartment() {
    }

    @Test
    public void addCart() {
    }

    @Test
    public void modifyCart() {

    }

    @Test
    public void removeCart() {
    }

    @Test
    public void setCartEmergency() {
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Long id = Long.valueOf(new Random().nextInt(104) + 1);
            set.add(id);
        }
        for (Long id : set) {
            service.setCartEmergency(id);
        }
    }

    @Test
    public void setCartInventory() {
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Long id = Long.valueOf(new Random().nextInt(104) + 1);
            set.add(id);
        }
        for (Long id : set) {
            service.setCartInventory(id);
        }
    }

}
package com.daniel.cart.mapper;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.res.CartBlockRes;
import com.daniel.cart.domain.vo.CartVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CartMapperTest {

    @Autowired
    CartMapper mapper;

    @Test
    public void findAll() {
        for (Cart cart : mapper.findAll()) {
            System.out.println(cart);
        }

    }

    @Test
    public void findAllByLimit() {
        CartVo cartVo = new CartVo();
        cartVo.setDepartmentName("心");
        cartVo.setStart(1);
        cartVo.setPageSize(5);
        System.out.println(cartVo.toString());
        for (Cart cart : mapper.findAllByLimit(cartVo)) {
            System.out.println(cart.toString());
        }
    }

    @Test
    public void findVacant() {
        for (Cart cart : mapper.findVacant()) {
            System.out.println(cart.toString());
        }
    }

    @Test
    public void getById() {
    }

    @Test
    public void getCountByState() {
    }

    @Test
    public void addCart() {
//        for (int i = 0; i < 100; i++) {
//            Cart cart = new Cart();
//            cart.setDepartmentId(Long.valueOf(new Random().nextInt(13) + 1l));
//            mapper.addCart(cart);
//        }

        Cart cart = new Cart();
        mapper.addCart(cart);
    }

    @Test
    public void removeById() {
    }

    @Test
    public void modifyCart() {
        Cart cart = new Cart();
        cart.setDepartmentId(3l);
        cart.setId(4l);
        mapper.modifyCart(cart);
    }

    @Test
    public void findCartWithBlock() {
        List<CartBlockRes> cartWithBlock = mapper.findCartWithBlock();
        for (CartBlockRes cartBlockRes : cartWithBlock) {
            System.out.println(cartBlockRes.toString());
        }
    }
}
package com.daniel.cart.mapper;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.CartOperateLog;
import com.daniel.cart.domain.enums.CartStateEnum;
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
public class CartOperateLogMapperTest {

    @Autowired
    CartOperateLogMapper mapper;
    @Autowired
    CartMapper cartMapper;

    @Test
    public void findAll() {
        for (CartOperateLog cartOperateLog : mapper.findAll()) {
            System.out.println(cartOperateLog.toString());
        }
    }

    @Test
    public void findByLimit() {
    }

    @Test
    public void getCount() {
    }

    @Test
    public void add() {
        Cart cart = cartMapper.getById(5L);
        CartOperateLog cartOperateLog = new CartOperateLog();
        cartOperateLog.setCart(cart);
        cartOperateLog.setOperateType(CartStateEnum.inventory);
        cartOperateLog.setEmployeeId(4L);
        mapper.add(cartOperateLog);

    }
}
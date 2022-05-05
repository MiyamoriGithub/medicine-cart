package com.daniel.cart.domain;

import com.daniel.cart.domain.enums.CartStateEnum;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * e
 *
 * @author Daniel Zheng
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class test {
    @Test
    public void test() {
//        String state = "free";
//        System.out.println(EnumUtils.isValidEnum(CartStateEnum.class, state));

        for (CartStateEnum value : CartStateEnum.values()) {
            System.out.println(value.name());
            System.out.println(value.getName());
        }
    }

}

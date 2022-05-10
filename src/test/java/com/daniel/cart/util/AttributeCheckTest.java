package com.daniel.cart.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AttributeCheckTest {

    @Test
    public void isIdOk() {
    }

    @Test
    public void testIsIdOk() {
    }

    @Test
    public void isStringOk() {
    }

    @Test
    public void isPhoneOk() {
        String phone = "18813187584";
        System.out.println(AttributeCheck.isPhoneOk(phone));
    }
}
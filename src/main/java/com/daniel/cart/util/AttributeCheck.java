package com.daniel.cart.util;

/**
 * 工具类
 *
 * @author Daniel Zheng
 **/

public class AttributeCheck {
    public static Boolean isIdOk(Long id) {
        if(id == null || id < 0L) {
            return false;
        }
        return true;
    }

    public static Boolean isIdOk(Integer id) {
        if(id == null || id < 0) {
            return false;
        }
        return true;
    }

    public static Boolean isStringOk(String string) {
        if(string == null || string.length() == 0 || string == "" || string == " ") {
            return false;
        }
        return true;
    }
}

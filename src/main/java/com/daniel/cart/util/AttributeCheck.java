package com.daniel.cart.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author Daniel Zheng
 **/

public class AttributeCheck {

    private final static Integer PHONE_LENGTH = 11;

    public static Boolean isIdOk(Long id) {
        return null != id && id > 0L;
    }

    public static Boolean isIdOk(Integer id) {
        return null != id && id > 0;
    }

    public static Boolean isStringOk(String string) {
        return null != string && string.length() != 0 && !string.equals(" ");
    }

    public static Boolean isPhoneOk(String phone) {
        if(!isStringOk(phone)) {
            return false;
        }
//        String regex = "0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}|13[0-9]\\d{8}|15[1089]\\d{8}";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(phone);
//        return matcher.matches();
        return phone.length() == PHONE_LENGTH && phone.startsWith("1");
    }
}

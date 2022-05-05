package com.daniel.cart.exception;

import com.daniel.cart.domain.result.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Cart 操作的异常类
 *
 * @author Daniel Zheng
 **/

@Getter
@Setter
public class CartOperateException extends RuntimeException{
    private static final long serialVersionUID = 6L;

    private String message = ResultCodeEnum.CART_ERROR.getMessage();
    private Integer code = ResultCodeEnum.CART_ERROR.getCode();

    public CartOperateException() {}

    public CartOperateException(String message) {
        super(message);
        this.message = message;
    }

    public CartOperateException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CartOperateException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

}

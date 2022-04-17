package com.daniel.cart.exception;

import com.daniel.cart.domain.result.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrugOperateException extends RuntimeException{
    private static final long serialVersionUID = 3L;

    private String message;
    private Integer code;

    public DrugOperateException() {}

    public DrugOperateException(String message) {
        super(message);
        this.message = message;
    }

    public DrugOperateException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public DrugOperateException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

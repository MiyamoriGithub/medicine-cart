package com.daniel.cart.exception;

import com.daniel.cart.domain.result.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationException extends RuntimeException{
    private static final long serialVersionUID = 2L;

    private String message = ResultCodeEnum.AUTH_ERROR.getMessage();
    private Integer code = ResultCodeEnum.AUTH_ERROR.getCode();

    public AuthorizationException() {}

    public AuthorizationException(String message) {
        super(message);
        this.message = message;
    }

    public AuthorizationException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public AuthorizationException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

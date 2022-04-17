package com.daniel.cart.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationException extends RuntimeException{
    private static final long serialVersionUID = 2L;

    private String message;
    private Integer code;

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

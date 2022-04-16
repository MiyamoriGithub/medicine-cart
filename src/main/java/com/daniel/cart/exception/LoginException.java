package com.daniel.cart.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;
    private Integer code;

    public LoginException() {}

    public LoginException(String message) {
        super(message);
        this.message = message;
    }

    public LoginException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public LoginException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

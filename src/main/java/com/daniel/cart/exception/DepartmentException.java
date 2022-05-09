package com.daniel.cart.exception;

import com.daniel.cart.domain.result.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Department 操作异常类
 *
 * @author Daniel Zheng
 **/

@Getter
@Setter
public class DepartmentException extends RuntimeException{
    private static final long serialVersionUID = 8L;

    private String message = ResultCodeEnum.DEPARTMENT_ERROR.getMessage();
    private Integer code = ResultCodeEnum.DEPARTMENT_ERROR.getCode();

    public DepartmentException() {}

    public DepartmentException(String message) {
        super(message);
        this.message = message;
    }

    public DepartmentException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public DepartmentException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}

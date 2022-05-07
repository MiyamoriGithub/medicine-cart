package com.daniel.cart.exception;

import com.daniel.cart.domain.result.ResultCodeEnum;

/**
 * Employee 操作的异常类
 *
 * @author Daniel Zheng
 **/

public class EmployeeOperateException extends RuntimeException {
    private static final long serialVersionUID = 7L;

    private String message = ResultCodeEnum.EMPLOYEE_ERROR.getMessage();
    private Integer code = ResultCodeEnum.EMPLOYEE_ERROR.getCode();

    public EmployeeOperateException() {}

    public EmployeeOperateException(String message) {
        super(message);
        this.message = message;
    }

    public EmployeeOperateException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public EmployeeOperateException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}

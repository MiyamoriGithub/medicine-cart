package com.daniel.cart.exception;

import com.daniel.cart.domain.result.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Grid 操作异常类
 *
 * @author Daniel Zheng
 * @create 2022-05-01 21:57
 **/

@Getter
@Setter
public class GridOperateException extends RuntimeException{
    private static final long serialVersionUID = 5L;

    private String message = ResultCodeEnum.GRID_ERROR.getMessage();
    private Integer code = ResultCodeEnum.GRID_ERROR.getCode();

    public GridOperateException() {}

    public GridOperateException(String message) {
        super(message);
        this.message = message;
    }

    public GridOperateException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public GridOperateException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}

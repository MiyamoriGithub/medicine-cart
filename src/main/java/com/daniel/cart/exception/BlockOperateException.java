package com.daniel.cart.exception;

import com.daniel.cart.domain.result.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Block 操作异常类
 *
 * @author Daniel Zheng
 **/

@Getter
@Setter
public class BlockOperateException extends RuntimeException{
    private static final long serialVersionUID = 4L;

    private String message = ResultCodeEnum.BLOCK_ERROR.getMessage();
    private Integer code = ResultCodeEnum.BLOCK_ERROR.getCode();

    public BlockOperateException() {}

    public BlockOperateException(String message) {
        super(message);
        this.message = message;
    }

    public BlockOperateException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BlockOperateException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}

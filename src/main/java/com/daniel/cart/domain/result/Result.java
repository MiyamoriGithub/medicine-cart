package com.daniel.cart.domain.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
@ApiModel(value = "全局统一返回结果")
public class Result {

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回消息")
    private String message;

    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<>();

    public Result() {}

    public static Result ok() {
        Result result = new Result();
        result.success(ResultCodeEnum.SUCCESS.getSuccess());
        result.code(ResultCodeEnum.SUCCESS.getCode());
        result.message(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }


    public static Result error(){
        Result result = new Result();
        result.success(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        result.code(ResultCodeEnum.UNKNOWN_REASON.getCode());
        result.message(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return result;
    }

    public static Result error(ResultCodeEnum codeEnum) {
        Result result = new Result();
        result.success(codeEnum.getSuccess());
        result.code(codeEnum.getCode());
        result.message(codeEnum.getMessage());
        return result;
    }

    public static Result setResult(ResultCodeEnum resultCodeEnum){
        Result result = new Result();
        result.success(resultCodeEnum.getSuccess());
        result.code(resultCodeEnum.getCode());
        result.message(resultCodeEnum.getMessage());
        return result;
    }



    public Result success(Boolean success){
        this.success = success;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result code(Integer code) {
        this.code = code;
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.data = map;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

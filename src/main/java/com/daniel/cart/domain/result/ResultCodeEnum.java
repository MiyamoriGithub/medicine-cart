package com.daniel.cart.domain.result;

import lombok.Getter;
import lombok.ToString;


/**
 * 返回码枚举类
 */
@ToString
@Getter
public enum ResultCodeEnum {

    SUCCESS(true, 20000, "成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),

    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAM_ERROR(false, 21003, "参数不正确"),

    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    FILE_DELETE_ERROR(false, 21005, "文件刪除错误"),
    EXCEL_DATA_IMPORT_ERROR(false, 21006, "Excel数据导入错误"),

    URL_ENCODE_ERROR(false, 23001, "URL编码失败"),
    ILLEGAL_CALLBACK_REQUEST_ERROR(false, 23002, "非法回调请求"),
    FETCH_ACCESSTOKEN_FAILD(false, 23003, "获取accessToken失败"),
    FETCH_USERINFO_ERROR(false, 23004, "获取用户信息失败"),

    DEPARTMENT_ERROR(false, 22000, "部门信息相关异常"),
    DEPARTMENT_QUERY_ERROR(false, 22001, "部门信息查询异常"),
    DEPARTMENT_OPERATE_ERROR(false, 22002, "部门信息操作异常"),

    DRUG_ERROR(false, 24000, "药品相关异常"),
    DRUG_INF_MISS_ERROR(false, 24001, "药品信息缺失"),
    DRUG_OPERATE_ERROR(false, 24002, "药品操作异常"),
    DRUG_QUERY_ERROR(false, 24003, "药品查询异常"),

    BLOCK_ERROR(false, 25000, "Block相关异常"),
    BLOCK_QUERY_ERROR(false, 25001, "Block查询时异常"),
    BLOCK_OPERATE_ERROR(false, 25002, "Block操作时异常"),

    GRID_ERROR(false, 26000, "Grid 相关异常"),
    GRID_QUERY_ERROR(false, 26001, "Grid 查询时异常"),
    GRID_OPERATE_ERROR(false, 26002, "Grid 操作时异常"),

    CART_ERROR(false, 27000, "抢救车相关异常"),
    CART_QUERY_ERROR(false, 27001, "抢救车查询时异常"),
    CART_OPERATE_ERROR(false, 27002, "抢救车操作时异常"),


    AUTH_ERROR(false, 28000, "登录和授权相关异常"),
    LOGIN_PHONE_ERROR(false, 28009, "手机号码不正确或已被禁用"),
    LOGIN_MOBILE_ERROR(false, 28001, "账号不正确"),
    LOGIN_PASSWORD_ERROR(false, 28008, "用户名或密码不正确"),
    LOGIN_DISABLED_ERROR(false, 28002, "该用户已被禁用"),
    REGISTER_MOBILE_ERROR(false, 28003, "手机号已被注册"),
    LOGIN_AUTH(false, 28004, "需要登录"),
    LOGIN_ACL(false, 28005, "没有权限"),
    LOGIN_EXPIR_ERROR(false, 28006, "登录已过期或未登录"),

    EMPLOYEE_ERROR(false, 29000, "用户相关异常"),
    EMPLOYEE_QUERY_ERROR(false, 29001, "用户查询相关异常"),
    EMPLOYEE_OPERATE_ERROR(false, 29002, "用户操作相关异常"),
    EMPLOYEE_PHONE_ERROR(false, 29003, "手机号码格式错误");



    private Boolean success;        // 当前对服务器的请求是否成功完成响应
    private Integer code;           // 状态码
    private String message;         // 返回消息

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}

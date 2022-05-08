package com.daniel.cart.config;

import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.exception.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class MyGlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyGlobalExceptionHandler.class);

    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Result loginExceptionHandler(HttpServletRequest request, LoginException e) {
        logger.error("发生登录异常，原因是：" + e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Result authorizationExceptionHandler(HttpServletRequest request, AuthorizationException e) {
        logger.error("发生授权异常，原因是：" + e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Result authenticationExceptionHanlder(HttpServletRequest request, AuthenticationException e) {
        logger.error("发生认证异常，原因是" + e.getMessage());
        return Result.error(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public Result unauthenticatedExceptionHandler(HttpServletRequest request, UnauthenticatedException e) {
        logger.error("发生无权限认证异常：" + e.getMessage());
        return Result.error(ResultCodeEnum.LOGIN_ACL);
    }

    @ExceptionHandler(value = DrugOperateException.class)
    @ResponseBody
    public Result drugOperateExceptionHandler(HttpServletRequest request, DrugOperateException e) {
        logger.error("发生药品相关异常，原因是：" + e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

    @ExceptionHandler(value = BlockOperateException.class)
    @ResponseBody
    public Result blockOperateExceptionHandler(HttpServletRequest request, BlockOperateException e) {
        logger.error("发生 Block 相关异常" + e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

    @ExceptionHandler(value = GridOperateException.class)
    @ResponseBody
    public Result gridOperateExceptionHandler(HttpServletRequest request, GridOperateException e) {
        logger.error("发生 Grid 相关异常" + e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

    @ExceptionHandler(value = CartOperateException.class)
    @ResponseBody
    public Result cartOperateExceptionHandler(HttpServletRequest request, CartOperateException e) {
        logger.error("发生抢救车相关异常" + e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

//    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest request, Exception e){
        logger.error("发生未知异常" + e.getClass() + "，原因是:",e.getMessage());
        return Result.error().message("发生未知异常");
    }
}

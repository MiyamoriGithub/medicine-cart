package com.daniel.cart.handler;

import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.exception.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        logger.error("发生业务异常，原因是：" + e.getMessage());
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

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest request, Exception e){
        logger.error("发生未知异常" + e.getClass() + "，原因是:",e.getMessage());
        return Result.error();
    }
}

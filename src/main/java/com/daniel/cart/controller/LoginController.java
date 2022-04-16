package com.daniel.cart.controller;

import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.vo.LoginVo;
import com.daniel.cart.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "登录Controller", tags = {"登录访问接口"})
public class LoginController {

    private final LoginService service;

    @Autowired
    public LoginController(LoginService service) {
        this.service = service;
    }

    @ApiOperation("登录")
    @PostMapping("login")
    @ApiResponses(value = {@ApiResponse(code=20000, message = "成功")})
    public Result login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        LoginVo user = new LoginVo();
        user.setPhone(phone);
        user.setPassword(password);
        Boolean res = service.login(user);
        return Result.ok().data("res", res);
    }

}

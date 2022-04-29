package com.daniel.cart.controller;

import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "登录Controller", tags = {"登录访问接口"})
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

//    private final LoginService service;

//    @Autowired
//    public LoginController(LoginService service) {
//        this.service = service;
//    }

    @ApiOperation("登录")
    @PostMapping("login")
    @ApiResponses(value = {@ApiResponse(code=20000, message = "成功")})
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
//        LoginVo user = new LoginVo();
//        user.setPhone(phone);
//        user.setPassword(password);
//        Boolean res = service.login(user);
//        if(res) {
//            return Result.ok().data("res", "true");
//        } else {
//            return Result.error(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
//        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        subject.login(token);
        subject.getSession().setAttribute("phone", username);
        subject.getSession().setAttribute("password", password);
        if(subject.isAuthenticated()) {
            logger.info("认证成功");
            return Result.ok().data("res", "true");
        }
        return Result.error(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
    }

    @ApiOperation("退出")
    @GetMapping("logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()) {
            logger.error("登录已过期或未登录");
            return Result.error(ResultCodeEnum.LOGIN_EXPIR_ERROR);
        }
        subject.logout();
        logger.info("退出登录成功");
        return Result.ok().message("退出登录成功");
    }

}

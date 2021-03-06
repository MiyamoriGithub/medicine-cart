package com.daniel.cart.controller;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.mapper.EmployeeMapper;
import io.swagger.annotations.*;
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

    private final EmployeeMapper employeeMapper;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

//    private final LoginService service;

//    @Autowired
//    public LoginController(LoginService service) {
//        this.service = service;
//    }

    @ApiOperation("登录")
    @PostMapping("login")
    @ApiResponses(value = {@ApiResponse(code=20000, message = "成功")})
    public Result login(
            @RequestParam @ApiParam(value = "username", required = true) String username,
            @RequestParam @ApiParam(value = "password", required = true) String password
    ) {
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

    @ApiOperation("获取当前登录的用户信息")
    @GetMapping("userInf")
    public Result userInf() {
        Subject subject = SecurityUtils.getSubject();
        String principal = (String)subject.getPrincipal();
        Employee employee = employeeMapper.findByPhone(principal);
        return Result.ok().data("item", employee);
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

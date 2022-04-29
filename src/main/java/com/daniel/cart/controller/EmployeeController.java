package com.daniel.cart.controller;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.vo.EmployeeVo;
import com.daniel.cart.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "员工信息Controller", tags = {"员工信息管理接口"})
@RestController
@CrossOrigin
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @ApiOperation("查找全部")
    @RequiresRoles("manager")
    @GetMapping("findAll")
    public Result findAll() {
        List<Employee> allEmployees = service.findAll();
        return Result.ok().data("items", allEmployees);
    }

    @ApiOperation("分页查询员工信息")
    @GetMapping("findByPage")
    public Result findByPage(EmployeeVo employeeInfo) {
        List<Employee> employees = service.findAllByInfo(employeeInfo);
        return Result.ok().data("items", employees);
    }

    @ApiOperation("获得限制条件下的员工数量")
    @GetMapping("getCount")
    public Result getCount(EmployeeVo employeeInfo) {
        Long count = service.getCount(employeeInfo);
        return Result.ok().data("count", count);
    }

    @ApiOperation("通过id查询员工信息")
//    @RequiresRoles("manager")
    @GetMapping("findById")
    public Result findById(Long id) {
        Employee employee = service.findById(id);
        return Result.ok().data("employee", employee);
    }

    // Todo 判断手机号、密码等的格式
    @ApiOperation("修改员工信息")
    @PostMapping("modify")
    public Result modifyEmployee(Employee employee) {
        boolean res =  service.modifyEmployee(employee);
        if(res) {
            return Result.ok().message("修改成功");
        } else {
            return Result.error().message("数据不存在");
        }
    }

    @ApiOperation("通过id删除员工信息")
    public Result removeById(Long id) {
        boolean res = service.removeEmployee(id);
        if(res) {
            return Result.ok().message("删除成功");
        } else {
            return Result.error().message("数据不存在");
        }
    }

}

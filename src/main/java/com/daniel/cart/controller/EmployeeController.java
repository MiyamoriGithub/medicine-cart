package com.daniel.cart.controller;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.domain.vo.EmployeeVo;
import com.daniel.cart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("findAll")
    public Result findAll() {
        List<Employee> allEmployees = service.findAll();
        return Result.ok().data("items", allEmployees);
    }

    @GetMapping("findByPage")
    public Result findByPage(EmployeeVo employeeInfo) {
        List<Employee> employees = service.findAllByInfo(employeeInfo);
        return Result.ok().data("items", employees);
    }

    @GetMapping("getCount")
    public Result getCount(EmployeeVo employeeInfo) {
        Long count = service.getCount(employeeInfo);
        return Result.ok().data("count", count);
    }

    @GetMapping("findById")
    public Result findById(Long id) {
        Employee employee = service.findById(id);
        return Result.ok().data("employee", employee);
    }

    @RequestMapping("")
    public Result modifyEmployee(Employee employee) {
        boolean res =  service.modifyEmployee(employee);
        if(res) {
            return Result.ok().message("修改成功");
        } else {
            return Result.error().message("数据不存在");
        }
    }

    public Result removeById(Long id) {
        boolean res = service.removeEmployee(id);
        if(res) {
            return Result.ok().message("删除成功");
        } else {
            return Result.error().message("数据不存在");
        }
    }

}

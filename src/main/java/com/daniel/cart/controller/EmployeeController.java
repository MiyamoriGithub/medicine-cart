package com.daniel.cart.controller;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "员工信息Controller", tags = {"员工信息管理接口"})
@RestController
@CrossOrigin
@RequestMapping("employee")
public class EmployeeController{

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @ApiOperation("查询员工信息")
//    @RequiresRoles("manager")
    @GetMapping("find")
    public Result find(
            @RequestParam(value = "用户 id", required = false)Long id,
            @RequestParam(value = "手机号码", required = false) String phone,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        if(id != null) {
            Employee employee = service.findById(id);
            return Result.ok().data("item", employee);
        } else if(phone != null) {
            Employee employee = service.findByPhone(phone);
            return Result.ok().data("item", employee);
        } else if(start != null && pageSize != null) {
            List<Employee> employees = service.findAll(start, pageSize);
            Long count = service.getCount();
            return Result.ok().data("items", employees).data("count", count);
        } else {
            List<Employee> employees = service.findAll();
            Long count = service.getCount();
            return Result.ok().data("items", employees).data("count", count);
        }
    }

    @ApiOperation("通过姓名限定条件查询用户信息")
    @GetMapping("name")
    public Result name(
            @RequestParam("姓名限定条件") String nameCondition,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        List<Employee> employees;
        if(start == null || pageSize == null) {
            employees = service.findByName(nameCondition);
        } else {
            employees = service.findByName(nameCondition, start, pageSize);
        }
        Long count = service.getCountByName(nameCondition);
        return Result.ok().data("items", employees).data("count", count);
    }

    @ApiOperation("通过部门限定条件查询用户信息")
    @GetMapping("depart")
    public Result depart(
            @RequestParam(value = "部门名称", required = false) String departmentName,
            @RequestParam(value = "部门id", required = false) Long departmentId,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        List<Employee> employees;
        Long count;
        if(departmentId != null) {
            if(start == null || pageSize == null) {
                employees = service.findByDepartment(departmentId);
            } else {
                employees = service.findByDepartment(departmentId, start, pageSize);
            }
            count = service.getCountByDepartment(departmentId);
        } else if(departmentName != null){
            if(start == null || pageSize == null) {
                employees = service.findByDepartment(departmentName);
            } else {
                employees = service.findByDepartment(departmentName, start, pageSize);
            }
            count = service.getCountByDepartment(departmentName);
        } else {
            return Result.error().message("查询条件为空");
        }
        return Result.ok().data("items", employees).data("count", count);
    }

    @ApiOperation("通过角色限定条件查询用户信息")
    @GetMapping("role")
    public Result role(
            @RequestParam("角色，通过相应api获取枚举值") String role,
            @RequestParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(value = "每页信息数量" , required = false) Integer pageSize
    ) {
        List<Employee> employees;
        if(start == null || pageSize == null) {
            employees = service.findByRole(role);
        } else {
            employees = service.findByRole(role, start, pageSize);
        }
        Long count = service.getCountByRole(role);
        return Result.ok().data("items", employees).data("count", count);
    }

    @ApiOperation("获取全部角色枚举")
    @GetMapping("getAllRoles")
    public Result getAllRoles() {
        Map<String, String> roles = service.getAllRoles();
        return Result.ok().data("items", roles);
    }

    @ApiOperation("修改员工信息")
    @PostMapping("modify")
    public Result modify(@RequestBody Employee employee) {
        boolean res =  service.modifyEmployee(employee);
        if(res) {
            return Result.ok().data("msg", "操作成功");
        }
        return Result.error();
    }

    @ApiOperation("通过id删除员工信息")
    @GetMapping("remove")
    public Result remove(@RequestParam("待删除用户 Id") Long id) {
        boolean res = service.removeEmployee(id);
        if(res) {
            return Result.ok().data("msg", "操作成功");
        }
        return Result.error();
    }

    @ApiOperation("添加用户信息")
    @PostMapping("add")
    public Result add(@RequestBody Employee employee) {
        boolean res = service.addEmployee(employee);
        if(res) {
            return Result.ok().data("msg", "操作成功");
        }
        return Result.error();
    }

}

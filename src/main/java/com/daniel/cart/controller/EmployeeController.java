package com.daniel.cart.controller;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.enums.RoleEnum;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @ApiOperation("查询员工信息")
//    @RequiresRoles("manager")
    @GetMapping("find")
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "用户 id")Long id,
            @RequestParam(required = false) @ApiParam(value = "姓名限定条件") String nameCondition,
            @RequestParam(required = false) @ApiParam(value = "手机号码") String phone,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        if(id != null) {
            Employee employee = service.findById(id);
            return Result.ok().data("item", employee);
        } else if(phone != null) {
            Employee employee = service.findByPhone(phone);
            return Result.ok().data("item", employee);
        }
        List<Employee> employees = null;
        Long count = null;
        if(nameCondition != null) {
            count = service.getCountByName(nameCondition);
            if(start != null && pageSize != null) {
                employees = service.findByName(nameCondition, start, pageSize);
            } else {
                employees = service.findByName(nameCondition);
            }
        }else if(start != null && pageSize != null) {
            employees = service.findAll(start, pageSize);
            count = service.getCount();
        } else {
            employees = service.findAll();
            count = service.getCount();
        }
        return Result.ok().data("items", employees).data("count", count);
    }

    @ApiOperation("通过姓名限定条件查询用户信息")
    @GetMapping("name")
    public Result name(
            @RequestParam @ApiParam(value = "姓名限定条件", required = true) String nameCondition,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
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
            @RequestParam(required = false) @ApiParam(value = "部门名称") String departmentName,
            @RequestParam(required = false) @ApiParam(value = "部门id") Long departmentId,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
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
            @RequestParam @ApiParam(value = "角色，通过相应api获取枚举值", required = true) String role,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
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
        List<Map<String, String>> roles = service.getAllRoles();
        return Result.ok().data("items", roles);
    }

    @ApiOperation("修改员工信息")
    @GetMapping("modify")
    public Result modify(
            @RequestParam @ApiParam(value = "用户 id", required = true) Long id,
            @RequestParam(required = false) @ApiParam(value = "姓名") String name,
            @RequestParam(required = false) @ApiParam(value = "手机号码") String phone,
            @RequestParam(required = false) @ApiParam(value = "部门id") Long departmentId,
            @RequestParam(required = false) @ApiParam(value = "密码") String password,
            @RequestParam(required = false) @ApiParam(value = "权限") String role
    ) {
        Employee employee = service.findById(id);
        employee.setName(name);
        employee.setPhone(phone);
        employee.setDepartmentId(departmentId);
        employee.setPassword(password);
        if(RoleEnum.roleCheck(role)) {
            employee.setRole(RoleEnum.valueOf(role));
        }
        boolean res =  service.modify(employee);
        if(res) {
            return Result.ok().message("修改成功");
        }
        return Result.error().message("无可修改的员工信息");
    }

    @ApiOperation("通过id删除员工信息")
    @GetMapping("remove")
    public Result remove(
            @RequestParam @ApiParam(value = "待删除用户 Id",required = true) Long id
    ) {
        boolean res = service.remove(id);
        if(res) {
            return Result.ok().data("msg", "操作成功");
        }
        return Result.error();
    }

    @ApiOperation("添加用户信息")
    @GetMapping("add")
    public Result add(
            @RequestParam @ApiParam(value = "姓名", required = true) String name,
            @RequestParam @ApiParam(value = "手机号码", required = true) String phone,
            @RequestParam(required = false) @ApiParam(value = "部门id") Long departmentId,
            @RequestParam @ApiParam(value = "密码", required = true) String password,
            @RequestParam(required = false) @ApiParam(value = "权限") String role

    ) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setPhone(phone);
        employee.setDepartmentId(departmentId);
        employee.setPassword(password);
        if(RoleEnum.roleCheck(role)) {
            employee.setRole(RoleEnum.valueOf(role));
        }
        boolean res = service.add(employee);
        if(res) {
            return Result.ok().message("操作成功");
        }
        return Result.error().message("操作失败");
    }

}

package com.daniel.cart.controller;

import com.daniel.cart.domain.Department;
import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.DepartmentService;
import com.daniel.cart.util.AttributeCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Department Controller
 *
 * @author Daniel Zheng
 **/


@Api(value = "部门信息Controller", tags = {"部门信息管理接口"})
@RestController
@CrossOrigin
@RequestMapping("department")
public class DepartmentController {

    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @ApiOperation("查询部门信息")
    @GetMapping("find")
    public Result find(
            @RequestParam(required = false) @ApiParam(value = "部门 id")Long id,
            @RequestParam(required = false) @ApiParam(value = "名称查询条件") String nameCondition,
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）") Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        if(id != null) {
            Department department = service.findById(id);
            return Result.ok().data("item", department);
        }

        List<Department> departments;
        Long count = service.getCount();
        if(AttributeCheck.isStringOk(nameCondition)) {
            if(start != null && pageSize != null) {
                departments = service.findByName(nameCondition, start, pageSize);
            } else {
                departments = service.findByName(nameCondition);
            }
        } else if(start != null && pageSize != null) {
            departments = service.findAll(start, pageSize);
        } else {
            departments = service.findAll();
        }
        return Result.ok().data("items", departments).data("count", count);
    }

    @ApiOperation("添加部门信息")
    @GetMapping("add")
    public Result add(
            @RequestParam(required = true) @ApiParam(value = "部门名称") String name
    ) {
        Department department = new Department();
        department.setName(name);
        Boolean res = service.add(department);
        return returnMessage(res);
    }

    @ApiOperation("修改部门信息")
    @GetMapping("modify")
    public Result modify(
            @RequestParam() @ApiParam(value = "部门 id", required = true)Long id,
            @RequestParam(required = false) @ApiParam(value = "部门名称") String name
    ) {
        Department department = service.findById(id);
        if(name != null) {
            department.setName(name);
        }
        Boolean res = service.modify(department);
        return returnMessage(res);
    }

    @ApiOperation("删除部门信息")
    @GetMapping("remove")
    public Result remove(
            @RequestParam() @ApiParam(value = "部门 id", required = true)Long id
    ) {
        Boolean res = service.remove(id);
        return returnMessage(res);
    }

    private Result returnMessage(Boolean res) {
        if(res) {
            return Result.ok().message("操作成功");
        } else {
            return Result.error().message("操作失败");
        }
    }
}

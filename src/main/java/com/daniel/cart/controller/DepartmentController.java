package com.daniel.cart.controller;

import com.daniel.cart.domain.Department;
import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.result.Result;
import com.daniel.cart.service.DepartmentService;
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
            @RequestParam(required = false) @ApiParam(value = "起始条目（从 1 开始）", required = false) Integer start,
            @RequestParam(required = false) @ApiParam(value = "每页信息数量") Integer pageSize
    ) {
        if(id != null) {
            Department department = service.findById(id);
            return Result.ok().data("item", department);
        } else if(start != null && pageSize != null) {
            List<Department> departments = service.findAll(start, pageSize);
            Long count = service.getCount();
            return Result.ok().data("items", departments).data("count", count);
        } else {
            List<Department> departments = service.findAll();
            Long count = service.getCount();
            return Result.ok().data("items", departments).data("count", count);
        }
    }
}

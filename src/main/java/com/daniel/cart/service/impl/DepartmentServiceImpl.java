package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Department;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.DepartmentVo;
import com.daniel.cart.exception.CartOperateException;
import com.daniel.cart.exception.DepartmentException;
import com.daniel.cart.exception.GridOperateException;
import com.daniel.cart.mapper.DepartmentMapper;
import com.daniel.cart.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.daniel.cart.util.AttributeCheck.isIdOk;
import static com.daniel.cart.util.AttributeCheck.isStringOk;

/**
 * Department Service 层接口的实现类
 *
 * @author Daniel Zheng
 **/

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentMapper mapper;

    public DepartmentServiceImpl(DepartmentMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public List<Department> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Department> findAll(Integer start, Integer pageSize) {
        return findByLimit(null, start, pageSize);
    }

    @Override
    public Long getCount() {
        return mapper.getCount();
    }

    @Override
    public Department findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Boolean add(Department department) {
        // 没有需要检查的条件
        return mapper.add(department) > 0;
    }

    @Override
    public Boolean modify(Department department) {
        idCheck(department.getDepartmentId(), "id");
        if(mapper.findById(department.getDepartmentId()) == null) {
            throw new DepartmentException(ResultCodeEnum.DEPARTMENT_OPERATE_ERROR.getCode(), "待修改的部门不存在");
        }
        stringCheck(department.getName());
        return mapper.modify(department) > 0;
    }

    @Override
    public Boolean remove(Long id) {
        idCheck(id, "id");
        if(mapper.findById(id) == null) {
            throw new DepartmentException(ResultCodeEnum.DEPARTMENT_OPERATE_ERROR.getCode(), "待删除部门信息不存在");
        }
        return mapper.remove(id) > 0;
    }

    @Override
    public List<Department> findByName(String name) {
        return findByName(name, null, null);
    }

    @Override
    public List<Department> findByName(String name, Integer start, Integer pageSize) {
        return findByLimit(name, start, pageSize);
    }

    private List<Department> findByLimit(String nameCondition, Integer start, Integer pageSize) {
        DepartmentVo limit = new DepartmentVo();
        limit.setNameCondition(nameCondition);
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findByLimit(limit);
    }

    private void idCheck(Long id, String name) {
        if(!isStringOk(name)) {
            name = "id";
        }
        if(!isIdOk(id)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), name + "信息缺失");
        }
    }

    private void idCheck(Integer id, String name) {
        if(!isStringOk(name)) {
            name = "id";
        }
        if(!isIdOk(id)) {
            throw new GridOperateException(ResultCodeEnum.GRID_QUERY_ERROR.getCode(), name + "信息缺失");
        }
    }

    private void stringCheck(String string) {
        if(!isStringOk(string)) {
            throw new DepartmentException(ResultCodeEnum.DEPARTMENT_QUERY_ERROR.getCode(), "部门名称信息异常");
        }
    }
}
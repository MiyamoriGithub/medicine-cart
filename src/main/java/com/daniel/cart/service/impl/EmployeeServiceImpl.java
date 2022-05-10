package com.daniel.cart.service.impl;

import com.daniel.cart.controller.LoginController;
import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.enums.RoleEnum;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.EmployeeVo;
import com.daniel.cart.exception.EmployeeOperateException;
import com.daniel.cart.mapper.DepartmentMapper;
import com.daniel.cart.mapper.EmployeeMapper;
import com.daniel.cart.service.EmployeeService;
import com.daniel.cart.util.AttributeCheck;
import com.daniel.cart.util.Md5Utils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daniel.cart.util.AttributeCheck.*;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper mapper;
    private final DepartmentMapper departmentMapper;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    @Autowired
    public EmployeeServiceImpl(EmployeeMapper mapper, DepartmentMapper departmentMapper) {
        this.mapper = mapper;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<Employee> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Employee> findAll(Integer start, Integer pageSize) {
        return findAllByLimit(null, null, null, null, start, pageSize);
    }

    @Override
    public List<Employee> findByName(String name) {
        return findByName(name, null, null);
    }

    @Override
    public List<Employee> findByName(String name, Integer start, Integer pageSize) {
        return findAllByLimit(name, null, null, null, start, pageSize);
    }

    @Override
    public List<Employee> findByDepartment(String departmentName) {
        return findByDepartment(departmentName, null, null);
    }

    @Override
    public List<Employee> findByDepartment(String departmentName, Integer start, Integer pageSize) {
        departmentNameCheck(departmentName);
        return findAllByLimit(null, null, departmentName, null, start, pageSize);
    }

    @Override
    public List<Employee> findByDepartment(Long departmentId) {
        return findByDepartment(departmentId, null, null);
    }

    @Override
    public List<Employee> findByDepartment(Long departmentId, Integer start, Integer pageSize) {
        departmentIdCheck(departmentId);
        return findAllByLimit(null, departmentId, null, null, start, pageSize);
    }

    @Override
    public List<Employee> findByRole(String role) {
        return findByRole(role, null, null);
    }

    @Override
    public List<Employee> findByRole(String role, Integer start, Integer pageSize) {
        roleCheck(role);
        return findAllByLimit(null, null, null, RoleEnum.valueOf(role), start, pageSize);
    }

    @Override
    public Employee findById(Long id) {
        idCheck(id);
        return mapper.findById(id);
    }

    @Override
    public Employee findByPhone(String phone) {
        phoneCheck(phone);
        return mapper.findByPhone(phone);
    }

    @Override
    public Long getCount() {
        return mapper.getCountByLimit(new EmployeeVo());
    }

    @Override
    public Long getCountByName(String name) {
        return getCountByLimit(name, null, null, null);
    }

    @Override
    public Long getCountByDepartment(String departmentName) {
        departmentNameCheck(departmentName);
        return getCountByLimit(null, null, departmentName, null);
    }

    @Override
    public Long getCountByDepartment(Long departmentId) {
        idCheck(departmentId);
        return getCountByLimit(null, departmentId, null, null);
    }

    @Override
    public Long getCountByRole(String role) {
        roleCheck(role);
        return getCountByLimit(null, null, null, RoleEnum.valueOf(role));
    }

    @Override
    public Boolean add(Employee employee) {
        /*
            因为删除本质上是将数据禁用，因此添加数据时会出现手机号重复的问题，也就是 DuplicateKeyException
            这个时候就需要将原数据重新启用并进行更新操作
         */
        if(employee == null) {
            return false;
        }
        phoneCheck(employee.getPhone());
        stringCheck(employee.getName(), "name");
//        idCheck(employee.getDepartmentId());
        stringCheck(employee.getName(), "password");
        employee.setPassword(Md5Utils.code(employee.getPassword()));
        Long res = 0L;
        try {
            res = mapper.addEmployee(employee);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            res = mapper.enableEmployee(employee.getPhone());
            Long id = mapper.findByPhone(employee.getPhone()).getId();
            employee.setId(id);
            employee.setAddTime(new Date(new java.util.Date().getTime()));
            mapper.modifyEmployee(employee);
        }
        return res > 0;
    }

    @Override
    public Boolean modify(Employee employee) {
        if(employee == null || employee.getId() == null) {
            return false;
        }
        if(AttributeCheck.isStringOk(employee.getPassword())) {
            employee.setPassword(Md5Utils.code(employee.getPassword()));
        }
        return mapper.modifyEmployee(employee) > 0;
    }

    @Override
    public Boolean remove(Long id) {
        idCheck(id);
        if(null == findById(id)) {
            throw new EmployeeOperateException(ResultCodeEnum.EMPLOYEE_OPERATE_ERROR.getCode(), "待删除的用户信息不存在");
        }
        return mapper.disableEmployee(id) > 0;
    }

    @Override
    public Map<String, String> getAllRoles() {
        Map<String, String> map = new HashMap<>();
        for (RoleEnum value : RoleEnum.values()) {
            map.put(value.name(), value.getName());
        }
        return map;
    }

    private Long getCountByLimit(String name, Long departmentId, String departmentName, RoleEnum roleEnum) {
        EmployeeVo limit = new EmployeeVo();
        limit.setNameCondition(name);
        limit.setDepartmentId(departmentId);
        limit.setDepartmentName(departmentName);
        limit.setRole(roleEnum);
        return mapper.getCountByLimit(limit);
    }

    private List<Employee> findAllByLimit(String name, Long departmentId, String departmentName, RoleEnum roleEnum, Integer start, Integer pageSize) {
        EmployeeVo limit = new EmployeeVo();
        limit.setNameCondition(name);
        limit.setDepartmentId(departmentId);
        limit.setDepartmentName(departmentName);
        limit.setRole(roleEnum);
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findAllByLimit(limit);
    }

    private void idCheck(Long id) {
        if(!isIdOk(id)) {
            throw new EmployeeOperateException(ResultCodeEnum.EMPLOYEE_OPERATE_ERROR.getCode(), "id 信息缺失");
        }
    }

    private void stringCheck(String string, String name) {
        if(!isStringOk(string)) {
            throw new EmployeeOperateException(ResultCodeEnum.EMPLOYEE_PHONE_ERROR.getCode(), name + "信息缺失");
        }
    }

    private void phoneCheck(String phone) {
        if(!isPhoneOk(phone)) {
            throw new EmployeeOperateException(ResultCodeEnum.EMPLOYEE_PHONE_ERROR.getCode(), ResultCodeEnum.EMPLOYEE_PHONE_ERROR.getMessage());
        }
    }

    private void roleCheck(String role) {
        if(!isStringOk(role) || !EnumUtils.isValidEnum(RoleEnum.class, role)) {
            throw new EmployeeOperateException(ResultCodeEnum.EMPLOYEE_QUERY_ERROR.getCode(), "角色信息缺失");
        }
    }

    private void departmentNameCheck(String departmentName) {
        if(!isStringOk(departmentName)) {
            throw new EmployeeOperateException(ResultCodeEnum.EMPLOYEE_QUERY_ERROR.getCode(), "部门名称信息缺失");
        }
    }

    private void departmentIdCheck(Long departmentId) {
        if(!isIdOk(departmentId)) {
            throw new EmployeeOperateException(ResultCodeEnum.EMPLOYEE_QUERY_ERROR.getCode(), "部门 id 信息缺失");
        }
        if(departmentMapper.findById(departmentId) == null) {
            throw new EmployeeOperateException(ResultCodeEnum.EMPLOYEE_QUERY_ERROR.getCode(), "对应部门信息不存在");
        }
    }
}

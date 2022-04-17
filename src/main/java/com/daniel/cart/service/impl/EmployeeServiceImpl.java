package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.vo.EmployeeVo;
import com.daniel.cart.mapper.EmployeeMapper;
import com.daniel.cart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper mapper;
    private static final Integer DEFAULT_START = 0;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Employee> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Employee> findAllByInfo(EmployeeVo info) {
        // 如果起始条目为空则设置为0
        if(info.getStart() == null) {
            info.setStart(DEFAULT_START);
        }
        List<Employee> employees = mapper.findAllByLimit(info);
        return employees;
    }

    @Override
    public Employee findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Employee findByPhone(String phone) {
        return mapper.findByPhone(phone);
    }

    @Override
    public Long getCount(EmployeeVo info) {
        return mapper.getCountByLimit(info);
    }

    @Override
    public boolean modifyEmployee(Employee employee) {
        mapper.modifyEmployee(employee);
        return true;
    }

    @Override
    public boolean removeEmployee(Long id) {
        mapper.removeEmployee(id);
        return true;
    }
}

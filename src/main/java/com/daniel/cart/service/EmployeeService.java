package com.daniel.cart.service;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.vo.EmployeeVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    /**
     * 查询所有 Employees 信息
     * @return Employee 类对象的 List 集合
     */
    public List<Employee> findAll();

    public List<Employee> findAllByInfo(EmployeeVo info);

    /**
     * 根据 id 查询 Employee 信息
     * @param id 雇员 id
     * @return Employee 类型对象
     */
    public Employee  findById(Long id);

    public Employee findByPhone(String phone);

    /**
     * 查询雇员信息条目总数
     * @return 条目总数
     */
    public Long getCount(EmployeeVo info);

    public boolean modifyEmployee(Employee employee);

    boolean removeEmployee(Long id);
}

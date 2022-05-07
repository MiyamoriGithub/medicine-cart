package com.daniel.cart.service;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.vo.EmployeeVo;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

@Service
public interface EmployeeService {

    List<Employee> findAll();

    List<Employee> findAll(Integer start, Integer pageSize);

    List<Employee> findByName(String name);

    List<Employee> findByName(String name, Integer start, Integer pageSize);

    List<Employee> findByDepartment(String departmentName);

    List<Employee> findByDepartment(String departmentName, Integer start, Integer pageSize);

    List<Employee> findByDepartment(Long departmentId);

    List<Employee> findByDepartment(Long departmentId, Integer start, Integer pageSize);

    List<Employee> findByRole(String role);

    List<Employee> findByRole(String role, Integer start, Integer pageSize);

    Employee  findById(Long id);

    Employee findByPhone(String phone);

    Long getCount();

    Long getCountByName(String name);

    Long getCountByDepartment(String departmentName);

    Long getCountByDepartment(Long departmentId);

    Long getCountByRole(String role);

    Boolean addEmployee(Employee employeee);

    Boolean modifyEmployee(Employee employee);

    Boolean removeEmployee(Long id);

    Map<String, String> getAllRoles();
}

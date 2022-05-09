package com.daniel.cart.service;

import com.daniel.cart.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmployeeService extends AbstractService<Employee> {

    List<Employee> findByName(String name);

    List<Employee> findByName(String name, Integer start, Integer pageSize);

    List<Employee> findByDepartment(String departmentName);

    List<Employee> findByDepartment(String departmentName, Integer start, Integer pageSize);

    List<Employee> findByDepartment(Long departmentId);

    List<Employee> findByDepartment(Long departmentId, Integer start, Integer pageSize);

    List<Employee> findByRole(String role);

    List<Employee> findByRole(String role, Integer start, Integer pageSize);

    Employee findByPhone(String phone);

    Long getCountByName(String name);

    Long getCountByDepartment(String departmentName);

    Long getCountByDepartment(Long departmentId);

    Long getCountByRole(String role);

    Map<String, String> getAllRoles();
}

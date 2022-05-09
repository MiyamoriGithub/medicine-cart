package com.daniel.cart.service;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.enums.RoleEnum;
import com.daniel.cart.util.Md5Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService ;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAll() {
        for (Employee employee : employeeService.findAll()) {
            System.out.println(employee);
        }
    }

//    @Test
//    public void findAllByInfo() {
//        EmployeeVo info = new EmployeeVo();
//        info.setStart(0);
//        info.setPageSize(20);
//        info.setNameCondition("王");
//        for (Employee employee : employeeService.findAllByInfo(info)) {
//            System.out.println(employee);
//        }
//    }

    @Test
    public void addEmployee() {
        Employee employee = new Employee();
        employee.setDepartmentId(11L);
        employee.setRole(RoleEnum.valueOf("admin"));
        employee.setPhone("17009305325");
        employee.setName("测试王");
        employee.setPassword(Md5Utils.code("1234567"));
        employeeService.add(employee);
    }
}
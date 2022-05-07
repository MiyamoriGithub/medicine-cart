package com.daniel.cart.mapper;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.vo.EmployeeVo;
import com.daniel.cart.util.Md5Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void findAll() {
        List<Employee> employees = mapper.findAll();
        for(Employee employee : employees) {
            System.out.println(employee.toString());
        }
    }

    @Test
    public void findAllByLimit() {
        EmployeeVo limit = new EmployeeVo();
        limit.setStart(0);
        limit.setPageSize(10);
        limit.setNameCondition("%张%");
        List<Employee> employees = mapper.findAllByLimit(limit);
        for(Employee employee : employees) {
            System.out.println(employee.toString());
        }
    }

    @Test
    public void findById() {
        System.out.println(mapper.findById(1l).toString());
    }

    @Test
    public void getCount() {
        EmployeeVo limit = new EmployeeVo();
        System.out.println(mapper.getCountByLimit(limit));
    }

    @Test
    public void modifyEmployee() {
//        Employee employee = new Employee();
//        employee.setId(1l);
//        employee.setPassword(Md5Utils.code("123456"));
//        mapper.modifyEmployee(employee);

//        for (int i = 1; i <= 300; i++) {
//            Employee employee = new Employee();
//            employee.setId((long)i);
//            employee.setDepartmentId((long)new Random().nextInt(13) + 1);
//            mapper.modifyEmployee(employee);
//        }

        for (int i = 1; i <= mapper.getCountByLimit(new EmployeeVo()); i++) {
            Employee employee = new Employee();
            employee.setId((long) i);
            employee.setPassword(Md5Utils.code("123456"));
            mapper.modifyEmployee(employee);
        }
    }

    @Test
    public void addEmployee() {
        Employee employee = new Employee();
        employee.setPassword(Md5Utils.code("123456"));
        employee.setPhone("17009305325");
        employee.setName("测试王");
        mapper.addEmployee(employee);
    }
}
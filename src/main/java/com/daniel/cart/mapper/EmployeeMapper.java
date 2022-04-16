package com.daniel.cart.mapper;

import com.daniel.cart.domain.Employee;
import com.daniel.cart.domain.vo.EmployeeVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeMapper {

    /**
     * 查询所有 Employee 信息
     * @return Employee 类型对象的 List 集合
     */
    List<Employee> findAll();

    /**
     * 分页查询 Employee 信息
     * @param limit Map<String, Integer> 存储两个键值对
     *              start 键值对表示开始查询索引，注意 MySql 索引从 0 开始
     *              pageSize 键值对表示每页的结果数
     * @return Employee 类型对象的 List 集合
     */
    List<Employee> findAllByLimit(EmployeeVo limit);

//    List<Employee> findAllByLimit

    /**
     * 根据 id 查询 Employee 信息
     * @param id 雇员 id
     * @return 包含用户信息的 Employee 类型对象
     */
    Employee findById(Long id);

    Employee findByPhone(String phone);

    /**
     * 查询数据库中条目总数
     * @return 条目总数
     */
    Long getCount(EmployeeVo info);

    Long addEmployee(Employee employee);

    Long modifyEmployee(Employee employee);

    Long removeEmployee(Long id);
}

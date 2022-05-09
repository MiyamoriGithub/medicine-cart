package com.daniel.cart.mapper;

import com.daniel.cart.domain.Department;
import com.daniel.cart.domain.vo.DepartmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {

    List<Department> findAll();

    List<Department> findByLimit(DepartmentVo limit);

    Long getCount();

    Long getCountByLimit(DepartmentVo limit);

    Department findById(Long id);

    Long add(Department department);

    Long modify(Department department);

    Long remove(Long id);

}

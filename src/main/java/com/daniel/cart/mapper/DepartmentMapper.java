package com.daniel.cart.mapper;

import com.daniel.cart.domain.Department;
import com.daniel.cart.domain.vo.DepartmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {

    public List<Department> findAll();

    public List<Department> findAllByLimit(DepartmentVo limit);

    public Department findById(Long id);


}

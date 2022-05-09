package com.daniel.cart.service;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.Department;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.invoke.CallSite;
import java.util.List;

/**
 * Department 的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
@Repository
public interface DepartmentService extends AbstractService<Department> {

    List<Department> findByName(String name);

    List<Department> findByName(String name, Integer start, Integer pageSize);

}

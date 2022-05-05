package com.daniel.cart.service.impl;

import com.daniel.cart.mapper.DepartmentMapper;
import com.daniel.cart.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Department Service 层接口的实现类
 *
 * @author Daniel Zheng
 **/

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentMapper mapper;

    public DepartmentServiceImpl(DepartmentMapper mapper) {
        this.mapper = mapper;
    }
}

package com.daniel.cart.service;

import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.mapper.DrugInfMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 药品信息的 Service 层接口
 *
 * @author Daniel Zheng
 **/

// Todo 完善接口

@Service
@Repository
public interface DrugInfService {

    DrugInf findById(Long id);

}

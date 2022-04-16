package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.mapper.DrugMapper;
import com.daniel.cart.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugServiceImpl implements DrugService {

    private final DrugMapper drugMapper;

    @Autowired
    public DrugServiceImpl(DrugMapper drugMapper) {
        this.drugMapper = drugMapper;
    }

    public List<Drug> findAll() {
        return drugMapper.findAll();
    }


}

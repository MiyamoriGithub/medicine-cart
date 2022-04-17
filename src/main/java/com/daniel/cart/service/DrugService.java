package com.daniel.cart.service;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.vo.DrugVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DrugService {

    public List<Drug> findAll();

    public List<Drug> findAllTemporary();

    public List<Drug> findAllExpire();

    public List<Drug> findByBarcode(String barcode);

    public List<Drug> findByLimit(DrugVo limit);

    public Drug findById(Long id);

    public Long getCountByLimit(DrugVo limit);

    public Boolean addDrug(Drug drug);

    public Boolean modifyDrug(Drug drug);

    public Boolean deleteDrug(Long id);

}

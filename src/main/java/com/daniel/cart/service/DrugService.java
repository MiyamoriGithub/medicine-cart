package com.daniel.cart.service;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.vo.DrugVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DrugService {

    List<Drug> findAll();

    List<Drug> findAll(Integer start, Integer pageSize);

    List<Drug> findTemporary();

    List<Drug> findTemporary(List<Drug> list);

    List<Drug> findExpire();

    List<Drug> findExpire(List<Drug> list);

    List<Drug> findByBarcode(String barcode);

    List<Drug> findByBarcode(String barcode, Integer start, Integer pageSize);

    List<Drug> findByName(String name);

    List<Drug> findByName(String name, Integer start, Integer pageSize);

//    List<Drug> findByLimit(DrugVo limit);

    Drug findById(Long id);

    Long getCount();

    Long getCountByLimit(DrugVo limit);

    Boolean addDrug(Drug drug);

    Boolean modifyDrug(Drug drug);

    Boolean deleteDrug(Long id);

}

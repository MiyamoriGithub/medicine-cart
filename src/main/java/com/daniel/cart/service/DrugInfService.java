package com.daniel.cart.service;

import com.daniel.cart.domain.DrugInf;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 药品信息的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
@Repository
public interface DrugInfService extends AbstractService<DrugInf> {

    List<DrugInf> findByName(String name);

    List<DrugInf> findByName(String name, Integer start, Integer pageSize);

    DrugInf findByBarcode(String barcode);
}

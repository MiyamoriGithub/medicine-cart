package com.daniel.cart.service;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.enums.DrugExceptionEnum;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public interface DrugService extends AbstractService<Drug> {

    List<Drug> findTemporary();

    List<Drug> findTemporary(List<Drug> list);

    Map<DrugExceptionEnum, HashSet<Long>> findException(List<Drug> list);

    List<Drug> findExpire();

    List<Drug> findExpire(List<Drug> list);

    List<Drug> findByDrugInfId(Long drugInfId);

    List<Drug> findByDrugInfId(Long id, Integer start, Integer pageSize);

    List<Drug> findByCart(Long cartId);

    List<Drug> findByCart(Long cartId, Integer start, Integer pageSize);

    List<Drug> findByBarcode(String barcode);

    List<Drug> findByBarcode(String barcode, Integer start, Integer pageSize);

    List<Drug> findByName(String name);

    List<Drug> findByName(String name, Integer start, Integer pageSize);

    /**
     * 添加特定批次药品的方法
     * @param drug 特定药品信息，注意在添加之前必须在数据库中已有对应的 drugInf 信息
     * @return 是否添加成功
     */
    Boolean add(Drug drug);

    Long getCountByDrugInfId(Long id);

    Long getCountByBarcode(String barcode);

    Long getCountByName(String nameCondition);

}

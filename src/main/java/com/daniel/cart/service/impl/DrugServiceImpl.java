package com.daniel.cart.service.impl;

import com.daniel.cart.controller.CartController;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.DrugVo;
import com.daniel.cart.exception.DrugOperateException;
import com.daniel.cart.mapper.DrugMapper;
import com.daniel.cart.service.DrugService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// Todo 细化实现逻辑

@Service("drugService")
@Transactional
public class DrugServiceImpl implements DrugService {

    private Logger logger = LoggerFactory.getLogger(DrugServiceImpl.class);

    // 临期药品定义为接近过期日期小于等于 90 天的药品
    public static final Integer TEMPORARY = 90;

    private final DrugMapper mapper;

    @Autowired
    public DrugServiceImpl(DrugMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Drug> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Drug> findAllTemporary() {
        List<Drug> list = new ArrayList<>();
        for (Drug drug : this.findAll()) {
            int days = this.getToExpire(drug);
            if(days > 0 && TEMPORARY >= days) {
                list.add(drug);
            }
        }
        return list;
    }

    @Override
    public List<Drug> findAllExpire() {
        List<Drug> list = new ArrayList<>();
        for (Drug drug : this.findAll()) {
            if(0 >= this.getToExpire(drug)) {
                list.add(drug);
            }
        }
        return list;
    }

    @Override
    public List<Drug> findByBarcode(String barcode) {
        DrugVo limit = new DrugVo();
        limit.setBarcode(barcode);
        List<Drug> drugs = this.findByLimit(limit);
        return drugs;
    }

    @Override
    public List<Drug> findByLimit(DrugVo limit) {
        return mapper.findAllByLimit(limit);
    }

    @Override
    public Drug findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Long getCountByLimit(DrugVo limit) {
        return mapper.getCountByLimit(limit);
    }

    @Override
    public Boolean addDrug(Drug drug) {
        long count = mapper.addDrug(drug);
        return count > 0;
    }

    @Override
    public Boolean modifyDrug(Drug drug) {
        long count = mapper.modifyDrug(drug);
        return count > 0;
    }

    @Override
    public Boolean deleteDrug(Long id) {
         long count = mapper.removeById(id);
         return count > 0;
    }

    private Integer getToExpire(Drug drug) {
        if(drug.getProductDate() == null || drug.getShelfLife() == null) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_INF_MISS_ERROR.getCode(), ResultCodeEnum.DRUG_INF_MISS_ERROR.getMessage());
        }
        int res = (int) ((System.currentTimeMillis() - drug.getProductDate().getTime()) / (1000 * 60 * 60 * 24));
        logger.info(drug.getName() + " : " + (drug.getShelfLife() - res));
        return drug.getShelfLife() - res;
    }
}

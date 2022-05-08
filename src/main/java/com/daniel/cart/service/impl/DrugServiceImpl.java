package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.DrugVo;
import com.daniel.cart.exception.DrugOperateException;
import com.daniel.cart.mapper.DrugInfMapper;
import com.daniel.cart.mapper.DrugMapper;
import com.daniel.cart.service.DrugService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.daniel.cart.util.AttributeCheck.isIdOk;
import static com.daniel.cart.util.AttributeCheck.isStringOk;

@Service("drugService")
@Transactional
public class DrugServiceImpl implements DrugService {

    private final Logger logger = LoggerFactory.getLogger(DrugServiceImpl.class);

    // 临期药品定义为接近过期日期小于等于 90 天的药品
    private static final Integer TEMPORARY = 90;
    private final DrugMapper mapper;
    private final DrugInfMapper drugInfMapper;

    @Autowired
    public DrugServiceImpl(DrugMapper mapper, DrugInfMapper drugInfMapper) {
        this.mapper = mapper;
        this.drugInfMapper = drugInfMapper;
    }

    @Override
    public List<Drug> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Drug> findAll(Integer start, Integer pageSize) {
        return findByLimit(null, null, null, start, pageSize);
    }

    @Override
    public List<Drug> findTemporary() {
        return findTemporary(this.findAll());
    }

    @Override
    public List<Drug> findTemporary(List<Drug> list) {
        List<Drug> res = new ArrayList<>();
        for (Drug drug : list) {
            int days = this.getToExpire(drug);
            if(days > 0 && TEMPORARY >= days) {
                res.add(drug);
            }
        }
        return res;
    }

    @Override
    public List<Drug> findExpire() {
        return findExpire(this.findAll());
    }

    @Override
    public List<Drug> findExpire(List<Drug> list) {
        List<Drug> res = new ArrayList<>();
        for (Drug drug : list) {
            if(0 >= this.getToExpire(drug)) {
                res.add(drug);
            }
        }
        return res;
    }

    @Override
    public List<Drug> findByDrugInfId(Long id) {
        return findByDrugInfId(id, null, null);
    }

    @Override
    public List<Drug> findByDrugInfId(Long id, Integer start, Integer pageSize) {
        return findByLimit(id, null, null, start, pageSize);
    }

    @Override
    public List<Drug> findByBarcode(String barcode) {
        return findByBarcode(barcode, null,null);
    }


    @Override
    public List<Drug> findByBarcode(String barcode, Integer start, Integer pageSize) {
        barcodeCheck(barcode);
        return findByLimit(null, barcode, null, start, pageSize);
    }

    @Override
    public List<Drug> findByName(String name) {
        return findByName(name, null, null);
    }

    @Override
    public List<Drug> findByName(String name, Integer start, Integer pageSize) {
        drugNameCheck(name);
        return findByLimit(null, null, name, start, pageSize);
    }

    @Override
    public Drug findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Long getCount() {
        return mapper.getCountByLimit(new DrugVo());
    }

    @Override
    public Long getCountByDrugInfId(Long id) {
        return getCountByLimit(id, null, null);
    }

    @Override
    public Long getCountByBarcode(String barcode) {
        barcodeCheck(barcode);
        return getCountByLimit(null, barcode, null);
    }

    @Override
    public Long getCountByName(String nameCondition) {
        drugNameCheck(nameCondition);
        return getCountByLimit(null, null, nameCondition);
    }

    @Override
    public Boolean addDrug(Drug drug) {
        long operate = 0L;
        if(drug != null) {
            operate = mapper.addDrug(drug);
            DrugInf res = null;
            if(drug.getDrugInfId() != null) {
                 res = drugInfMapper.findById(drug.getDrugInfId());
            } else if(drug.getDrugInf() != null) {
                 DrugInf inf = drug.getDrugInf();
                 barcodeCheck(inf.getBarcode());
                 res = drugInfMapper.findByBarcode(inf.getBarcode());
             }
            if(res == null) {
                DrugInf inf = drug.getDrugInf();
                barcodeCheck(inf.getBarcode());
                drugNameCheck(inf.getName());
                shelfLifeCheck(inf.getShelfLife());
                operate += drugInfMapper.addDrugInf(inf);
            }
        }
        return operate > 0L;
    }

    @Override
    public Boolean modifyDrug(Drug drug) {
        if(drug.getId() != null && drug.getDrugInf() != null && drug.getDrugInf().getDrugInfId() != null) {
            idCheck(drug.getId());
            DrugInf newInf = drug.getDrugInf();
            drugInfIdCheck(newInf.getDrugInfId());
            return mapper.modifyDrug(drug) > 0L;
        }
        return false;
    }

    @Override
    public Boolean deleteDrug(Long id) {
        idCheck(id);
        return mapper.removeById(id) > 0;
    }

    private Integer getToExpire(Drug drug) {
        if(drug.getProductDate() == null || drug.getDrugInf().getShelfLife() == null) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_INF_MISS_ERROR.getCode(), ResultCodeEnum.DRUG_INF_MISS_ERROR.getMessage());
        }
        int res = (int) ((System.currentTimeMillis() - drug.getProductDate().getTime()) / (1000 * 60 * 60 * 24));
//        logger.info(drug.getDrugInf().getName() + " : " + (drug.getDrugInf().getShelfLife() - res));
        return drug.getDrugInf().getShelfLife() - res;
    }

    private Long getCountByLimit(Long drugInfId, String barcode, String drugName) {
        DrugVo limit = new DrugVo();
        limit.setDrugInfId(drugInfId);
        limit.setBarcode(barcode);
        limit.setNameCondition(drugName);
        return mapper.getCountByLimit(limit);
    }

    private List<Drug> findByLimit(Long drugInfId, String barcode, String drugName, Integer start, Integer pageSize) {
        DrugVo limit = new DrugVo();
        limit.setDrugInfId(drugInfId);
        limit.setBarcode(barcode);
        limit.setNameCondition(drugName);
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findByLimit(limit);
    }

    private void idCheck(Long id) {
        if(!isIdOk(id)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_OPERATE_ERROR.getCode(), "药品 id 信息缺失");
        }
    }

    private void shelfLifeCheck(Integer shelfLife) {
        if(!isIdOk(shelfLife)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_OPERATE_ERROR.getCode(), "药品保质期信息缺失");
        }
    }

    private void drugInfIdCheck(Long id) {
        if(!isIdOk(id)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_QUERY_ERROR.getCode(), "药品通用信息 id 信息缺失");
        }
    }

    private void drugNameCheck(String drugName) {
        if(!isStringOk(drugName)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_QUERY_ERROR.getCode(), "药品名称信息缺失");
        }
    }

    private void barcodeCheck(String barcode) {
        if(!isStringOk(barcode)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_QUERY_ERROR.getCode(), "药品条码信息缺失");
        }
    }

}

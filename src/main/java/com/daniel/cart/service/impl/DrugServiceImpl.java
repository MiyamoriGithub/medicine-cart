package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.enums.CartExceptionEnum;
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

import java.util.*;

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
        long currentTimeMillis = System.currentTimeMillis();
        for (Drug drug : list) {
            int days = this.getToExpire(drug, currentTimeMillis);
            if(days > 0 && TEMPORARY >= days) {
                res.add(drug);
            }
        }
        return res;
    }

    @Override
    public Map<CartExceptionEnum, HashSet<Long>> findException(List<Drug> list) {
        HashSet<Long> expire = new HashSet<>();
        HashSet<Long> temporary = new HashSet<>();
        Map<CartExceptionEnum, HashSet<Long>> res = new HashMap<>();
        long currentTimeMillis = System.currentTimeMillis();
        for (Drug drug : list) {
            int days = this.getToExpire(drug, currentTimeMillis);
            if(days < 0) {
                expire.add(drug.getId());
            } else if(days > 0 && TEMPORARY >= days){
                temporary.add(drug.getId());
            }
        }
        res.put(CartExceptionEnum.expire, expire);
        res.put(CartExceptionEnum.temporary, temporary);
        return res;
    }

    @Override
    public List<Drug> findExpire() {
        return findExpire(this.findAll());
    }

    @Override
    public List<Drug> findExpire(List<Drug> list) {
        List<Drug> res = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();
        for (Drug drug : list) {
            if(0 >= this.getToExpire(drug, currentTimeMillis)) {
                res.add(drug);
            }
        }
        return res;
    }

    @Override
    public List<Drug> findByDrugInfId(Long drugInfId) {
        return findByDrugInfId(drugInfId, null, null);
    }

    @Override
    public List<Drug> findByDrugInfId(Long id, Integer start, Integer pageSize) {
        return findByLimit(null, id, null, start, pageSize);
    }

    @Override
    public List<Drug> findByCart(Long cartId) {
        return findByCart(cartId, null, null);
    }

    @Override
    public List<Drug> findByCart(Long cartId, Integer start, Integer pageSize) {
        idCheck(cartId, "cartId");
        return findByLimit(cartId, null, null, start, pageSize);
    }

    @Override
    public List<Drug> findByBarcode(String barcode) {
        return findByBarcode(barcode, null,null);
    }


    @Override
    public List<Drug> findByBarcode(String barcode, Integer start, Integer pageSize) {
        barcodeCheck(barcode);
        Long drugInfId = drugInfMapper.findByBarcode(barcode).getDrugInfId();
        return findByLimit(null, drugInfId, null, start, pageSize);
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
        return mapper.getCount();
    }

    @Override
    public Long getCountByDrugInfId(Long id) {
        return getCountByLimit(id, null);
    }

    @Override
    public Long getCountByBarcode(String barcode) {
        barcodeCheck(barcode);
        Long drugInfId = drugInfMapper.findByBarcode(barcode).getDrugInfId();
        return getCountByLimit(drugInfId, null);
    }

    @Override
    public Long getCountByName(String nameCondition) {
        drugNameCheck(nameCondition);
        return getCountByLimit(null, nameCondition);
    }

    @Override
    public Boolean add(Drug drug) {
        long operate = 0L;
        if(drug != null) {
            DrugInf res = null;
            // 通过 drug 中的 drugInfId 或者 barcode 获取drugInf，如果获取不到则通用信息添加失败
            if(drug.getDrugInfId() != null) {
                res = drugInfMapper.findById(drug.getDrugInfId());
            } else if(drug.getDrugInf() != null) {
                DrugInf inf = drug.getDrugInf();
                barcodeCheck(inf.getBarcode());
                res = drugInfMapper.findByBarcode(inf.getBarcode());
            }
            if(res == null) {
                throw new DrugOperateException("待添加的药品通用信息不存在");
            }
            operate = mapper.addDrug(drug);
        }
        return operate > 0L;
    }

    @Override
    public Boolean modify(Drug drug) {
        if(drug.getId() != null && drug.getDrugInf() != null && drug.getDrugInf().getDrugInfId() != null) {
            idCheck(drug.getId(), "drugId");
            DrugInf newInf = drug.getDrugInf();
            idCheck(newInf.getDrugInfId(), "drugInfId");
            return mapper.modifyDrug(drug) > 0L;
        }
        return false;
    }

    @Override
    public Boolean remove(Long id) {
        idCheck(id, "drugId");
        return mapper.removeById(id) > 0;
    }

    private Integer getToExpire(Drug drug, Long currentTimeMillis) {
        if(drug.getProductDate() == null || drug.getDrugInf().getShelfLife() == null) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_INF_MISS_ERROR.getCode(), ResultCodeEnum.DRUG_INF_MISS_ERROR.getMessage());
        }
        int res = (int) ((currentTimeMillis - drug.getProductDate().getTime()) / (1000 * 60 * 60 * 24));
//        logger.info(drug.getDrugInf().getName() + " : " + (drug.getDrugInf().getShelfLife() - res));
        return drug.getDrugInf().getShelfLife() - res;
    }

    private Long getCountByLimit(Long drugInfId, String drugName) {
        DrugVo limit = new DrugVo();
        limit.setDrugInfId(drugInfId);
        limit.setNameCondition(drugName);
        return mapper.getCountByLimit(limit);
    }

    private List<Drug> findByLimit(Long cartId, Long drugInfId, String drugName, Integer start, Integer pageSize) {
        DrugVo limit = new DrugVo();
        limit.setCartId(cartId);
        limit.setDrugInfId(drugInfId);
        limit.setNameCondition(drugName);
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findByLimit(limit);
    }

    private void idCheck(Long id, String name) {
        if(!isIdOk(id)) {
            logger.error(name + "信息缺失");
            throw new DrugOperateException(ResultCodeEnum.DRUG_OPERATE_ERROR.getCode(), name + "信息缺失");
        }
    }

    private void shelfLifeCheck(Integer shelfLife) {
        if(!isIdOk(shelfLife)) {
            logger.error("药品保质期信息缺失");
            throw new DrugOperateException(ResultCodeEnum.DRUG_OPERATE_ERROR.getCode(), "药品保质期信息缺失");
        }
    }

    private void drugNameCheck(String drugName) {
        if(!isStringOk(drugName)) {
            logger.error("药品名称信息缺失");
            throw new DrugOperateException(ResultCodeEnum.DRUG_QUERY_ERROR.getCode(), "药品名称信息缺失");
        }
    }

    private void barcodeCheck(String barcode) {
        if(!isStringOk(barcode)) {
            logger.error("药品条码信息缺失");
            throw new DrugOperateException(ResultCodeEnum.DRUG_QUERY_ERROR.getCode(), "药品条码信息缺失");
        }
    }

}

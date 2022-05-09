package com.daniel.cart.service.impl;

import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.domain.vo.DrugInfVo;
import com.daniel.cart.exception.DrugOperateException;
import com.daniel.cart.mapper.DrugInfMapper;
import com.daniel.cart.service.DrugInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.daniel.cart.util.AttributeCheck.isIdOk;
import static com.daniel.cart.util.AttributeCheck.isStringOk;

/**
 * DrugInf Service 接口的实现类
 *
 * @author Daniel Zheng
 **/

@Service("drugInfService")
@Transactional
public class DrugInfServiceImpl implements DrugInfService {

    private final DrugInfMapper mapper;

    @Autowired
    public DrugInfServiceImpl(DrugInfMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DrugInf> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<DrugInf> findAll(Integer start, Integer pageSize) {
        return findByLimit(null, start, pageSize);
    }

    @Override
    public Long getCount() {
        return mapper.getCount();
    }

    @Override
    public DrugInf findById(Long id) {
        idCheck(id, "drugInfId");
        return mapper.findById(id);
    }

    @Override
    public Boolean add(DrugInf drugInf) {
        if(drugInf.getBarcode() != null) {
            barcodeCheck(drugInf.getBarcode());
        }
        if(drugInf.getName() != null) {
            stringCheck(drugInf.getName());
        }
        if(drugInf.getShelfLife() != null) {
            idCheck(drugInf.getShelfLife(), "shelfLife");
        }
        return mapper.addDrugInf(drugInf) > 0;
    }

    @Override
    public Boolean modify(DrugInf drugInf) {
        idCheck(drugInf.getDrugInfId(), "drugInfId");
        DrugInf origin = mapper.findById(drugInf.getDrugInfId());
        if(origin == null) {
            throw new DrugOperateException("待修改的药品信息不存在");
        }
        if(drugInf.getBarcode() != null) {
            barcodeCheck(drugInf.getBarcode());
            origin.setBarcode(drugInf.getBarcode());
        }
        if(drugInf.getName() != null) {
            stringCheck(drugInf.getName());
            origin.setName(drugInf.getName());
        }
        if(drugInf.getShelfLife() != null) {
            idCheck(drugInf.getShelfLife(), "shelfLife");
            origin.setShelfLife(drugInf.getShelfLife());
        }
        if(drugInf.getDrugPackage() != null) {
            idCheck(drugInf.getDrugPackage(), "drug package");
            origin.setDrugPackage(drugInf.getDrugPackage());
        }
        return mapper.modifyDrugInf(origin) > 0;
    }

    @Override
    public Boolean remove(Long id) {
        idCheck(id, "drugInfId");
        return mapper.removeDrugInf(id) > 0;
    }

    @Override
    public List<DrugInf> findByName(String name) {
        return findByName(name, null, null);
    }

    @Override
    public List<DrugInf> findByName(String name, Integer start, Integer pageSize) {
        return findByLimit(name, start, pageSize);
    }

    @Override
    public DrugInf findByBarcode(String barcode) {
        barcodeCheck(barcode);
        return mapper.findByBarcode(barcode);
    }

    private List<DrugInf> findByLimit(String nameCondition, Integer start, Integer pageSize) {
        DrugInfVo limit = new DrugInfVo();
        limit.setNameCondition(nameCondition);
        limit.setStart(start);
        limit.setPageSize(pageSize);
        return mapper.findByLimit(limit);
    }

    private Long getCountByLimit(String nameCondition) {
        DrugInfVo limit = new DrugInfVo();
        limit.setNameCondition(nameCondition);
        return mapper.getCountByLimit(limit);
    }

    private void idCheck(Long id, String name) {
        if(!isIdOk(id)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_OPERATE_ERROR.getCode(), name + " 信息缺失");
        }
    }
    private void idCheck(Integer id, String name) {
        if(!isIdOk(id)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_OPERATE_ERROR.getCode(), name + " 信息缺失");
        }
    }

    private void stringCheck(String drugName) {
        if(!isStringOk(drugName)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_OPERATE_ERROR.getCode(), "药品名称信息缺失");
        }
    }

    private void barcodeCheck(String barcode) {
        if(!isStringOk(barcode)) {
            for (char c : barcode.toCharArray()) {
                // 如果存在字符不是数字那么字符串就不是条形码
                if(!Character.isDigit(c)) {
                    throw new DrugOperateException(ResultCodeEnum.DRUG_ERROR.getCode(), "二维码信息错误");
                }
            }

        }
    }
}

package com.daniel.cart.service.impl;

import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.result.ResultCodeEnum;
import com.daniel.cart.exception.DrugOperateException;
import com.daniel.cart.mapper.DrugInfMapper;
import com.daniel.cart.service.DrugInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.daniel.cart.util.AttributeCheck.isIdOk;

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
    public DrugInf findById(Long id) {
        idCheck(id);
        return mapper.findById(id);
    }


    private void idCheck(Long id) {
        if(!isIdOk(id)) {
            throw new DrugOperateException(ResultCodeEnum.DRUG_OPERATE_ERROR.getCode(), "药品信息 id 信息缺失");
        }
    }
}

package com.daniel.cart.mapper;

import com.daniel.cart.domain.DrugInf;
import com.daniel.cart.domain.vo.DrugInfVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 药品通用信息 Mapper 层接口
 *
 * @author Daniel Zheng
 **/

@Mapper
@Repository
public interface DrugInfMapper {
    List<DrugInf> findAll();

    List<DrugInf> findByLimit(DrugInfVo limit);

    DrugInf findById(Long id);

    DrugInf findByBarcode(String barcode);

    Long getCount();

    Long getCountByLimit(DrugInfVo limit);

    Long addDrugInf(DrugInf drugInf);

    Long modifyDrugInf(DrugInf drugInf);

    Long removeDrugInf(Long id);
}

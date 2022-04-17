package com.daniel.cart.mapper;

import com.daniel.cart.domain.Drug;
import com.daniel.cart.domain.vo.DrugVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DrugMapper {
    public List<Drug> findAll();

    public List<Drug> findAllByLimit(DrugVo limit);

    public Drug findById(Long id);

    public Long getCountByLimit(DrugVo limit);

    public Long addDrug(Drug drug);

    public Long removeById(Long id);

    public Long modifyDrug(Drug drug);
}

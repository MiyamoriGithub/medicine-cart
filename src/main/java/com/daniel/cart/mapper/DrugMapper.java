package com.daniel.cart.mapper;

import com.daniel.cart.domain.Drug;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DrugMapper {
    List<Drug> findAll();

}

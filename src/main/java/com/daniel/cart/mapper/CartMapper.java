package com.daniel.cart.mapper;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.vo.CartVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CartMapper {
    List<Cart> findAll();

    List<Cart> findAllByLimit(CartVo limit);

    Cart getById(Long id);

    Long getCountByLimit(CartVo limit);

    Long addCart(Cart cart);

    Long removeById(Long id);

    Long modifyCart(Cart cart);
}

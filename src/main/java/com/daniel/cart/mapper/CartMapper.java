package com.daniel.cart.mapper;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.CartStateEnum;
import com.daniel.cart.domain.vo.CartVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CartMapper {
    public List<Cart> findAll();

    public List<Cart> findAllByLimit(CartVo limit);

    public Cart getById(Long id);

    public Long getCountByState(CartVo limit);

    public Long addCart(Cart cart);

    public Long removeById(Long id);

    public Long modifyCart(Cart cart);
}

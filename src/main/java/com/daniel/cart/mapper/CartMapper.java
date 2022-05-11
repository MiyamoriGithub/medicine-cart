package com.daniel.cart.mapper;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.res.CartBlockRes;
import com.daniel.cart.domain.vo.CartVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Mapper
@Repository
public interface CartMapper {
    List<Cart> findAll();

    List<Cart> findAllByLimit(CartVo limit);

    /**
     * 查找为空的 cart
     * @return 急救车列表
     */
    HashSet<Cart> findVacant();

    /**
     * 查找非空的 cart
     * @return 急救车列表
     */
    HashSet<Cart> findNotVacant();

    Cart getById(Long id);

    Long getCountByLimit(CartVo limit);

    Long addCart(Cart cart);

    Long removeById(Long id);

    Long modifyCart(Cart cart);

    /**
     * 查找带有 block 信息的 cart 列表
     * @return 封装在 CartBlockRes 结果类中
     */
    List<CartBlockRes> findCartWithBlock();
}

package com.daniel.cart.service.impl;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.enums.CartStateEnum;
import com.daniel.cart.domain.vo.CartVo;
import com.daniel.cart.mapper.CartMapper;
import com.daniel.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("cartService")
@Transactional
public class CartServiceImpl implements CartService {
    private final CartMapper mapper;

    @Autowired
    public CartServiceImpl(CartMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Cart> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Cart> findAllByDepartment(String departmentName) {
        CartVo limit = new CartVo();
        limit.setDepartmentName(departmentName);
        return mapper.findAllByLimit(limit);
    }

    @Override
    public List<Cart> findAllByDepartment(Long departmentId) {
        CartVo limit = new CartVo();
        limit.setDepartmentId(departmentId);
        return mapper.findAllByLimit(limit);
    }

    @Override
    public List<Cart> findAllByState(String state) {
        CartVo limit = new CartVo();

        limit.setState(CartStateEnum.valueOf(state));
        return mapper.findAllByLimit(limit);
    }

    @Override
    public Cart findById(Long id) {
        return mapper.getById(id);
    }

    @Override
    public Long getCountByState(String state) {
        CartVo limit = new CartVo();
        limit.setState(CartStateEnum.valueOf(state));
        return mapper.getCountByLimit(limit);
    }

    @Override
    public Long getCountByDepartment(Long departmentId) {
        CartVo limit = new CartVo();
        limit.setDepartmentId(departmentId);
        return (long)mapper.findAllByLimit(limit).size();
    }

    @Override
    public Long getCountByDepartment(String departmentName) {
        CartVo limit = new CartVo();
        limit.setDepartmentName(departmentName);
        return (long)mapper.findAllByLimit(limit).size();
    }

    @Override
    public Boolean addCart(Cart cart) {
        if(cart == null) {
            return false;
        }
        long res = mapper.addCart(cart);
        if(res > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean modifyCart(Cart cart) {
        if(cart == null || cart.getId() == null) {
            return false;
        }
        Long res = mapper.modifyCart(cart);
        if(res > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeCart(Long id) {
        if(id == null) {
            return false;
        }
        Long res = mapper.removeById(id);
        if(res > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean setCartFree(Long id) {
        return setCartState(id, CartStateEnum.free);
    }

    @Override
    public Boolean setCartInventory(Long id) {
        return setCartState(id, CartStateEnum.inventory);
    }

    @Override
    public Boolean setCartEmergency(Long id) {
        return setCartState(id, CartStateEnum.emergency);
    }

    private Boolean setCartState(Long id, CartStateEnum state) {
        if(id == null) {
            return false;
        }
        Cart cart = new Cart();
        cart.setId(id);
        cart.setState(state);
        return modifyCart(cart);
    }
}

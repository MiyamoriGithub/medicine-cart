package com.daniel.cart.service;

import com.daniel.cart.domain.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cart 实体类的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
public interface CartService {
    public List<Cart> findAll();

    public List<Cart> findAllByDepartment(String departmentName);

    public List<Cart> findAllByDepartment(Long departmentId);

    public List<Cart> findAllByState(String state);

    public Cart findById(Long id);

    public Long getCountByState(String state);

    public Long getCountByDepartment(Long departmentId);

    public Long getCountByDepartment(String departmentName);

    public Boolean addCart(Cart cart);

    public Boolean modifyCart(Cart cart);

    public Boolean removeCart(Long id);

    public Boolean setCartFree(Long id);

    public Boolean setCartInventory(Long id);

    public Boolean setCartEmergency(Long id);

}

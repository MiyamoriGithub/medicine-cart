package com.daniel.cart.service;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.enums.CartStateEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Cart 实体类的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
public interface CartService {
    List<Cart> findAll();

    List<Cart> findAll(Integer start, Integer pageSize);

    List<Cart> findAllByDepartment(String departmentName);

    List<Cart> findAllByDepartment(String departmentName, Integer start, Integer pageSize);

    List<Cart> findAllByDepartment(Long departmentId);

    List<Cart> findAllByDepartment(Long departmentId, Integer start, Integer pageSize);

    List<Cart> findAllByState(String state);

    List<Cart> findAllByState(String state, Integer start, Integer pageSize);

    List<Cart> findByLimit(Long departmentId, String state);

    List<Cart> findByLimit(Long departmentId, String state, Integer start, Integer pageSize);

    List<Cart> findByLimit(String departmentName, String state);

    List<Cart> findByLimit(String departmentName, String state, Integer start, Integer pageSize);

    Cart findById(Long id);

    Long getCountByState(String state);

    Long getCountByDepartment(Long departmentId);

    Long getCountByDepartment(String departmentName);

    Long getCountByLimit(Long departmentId, String state);

    Long getCountByLimit(String departmentName, String state);

    Boolean addCart(Cart cart);

    Boolean modifyCart(Cart cart);

    Boolean removeCart(Long id);

    Boolean setCartFree(Long id);

    Boolean setCartInventory(Long id);

    Boolean setCartEmergency(Long id);

    Map<String, String> getAllStates();

}

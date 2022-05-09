package com.daniel.cart.service;

import com.daniel.cart.domain.Cart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Cart 实体类的 Service 层接口
 *
 * @author Daniel Zheng
 **/

@Service
public interface CartService extends AbstractService<Cart> {

    List<Cart> findByDepartment(String departmentName);

    List<Cart> findByDepartment(String departmentName, Integer start, Integer pageSize);

    List<Cart> findByDepartment(Long departmentId);

    List<Cart> findByDepartment(Long departmentId, Integer start, Integer pageSize);

    List<Cart> findByState(String state);

    List<Cart> findByState(String state, Integer start, Integer pageSize);

    List<Cart> findByLimit(Long departmentId, String state);

    List<Cart> findByLimit(Long departmentId, String state, Integer start, Integer pageSize);

    List<Cart> findByLimit(String departmentName, String state);

    List<Cart> findByLimit(String departmentName, String state, Integer start, Integer pageSize);

    Long getCountByState(String state);

    Long getCountByDepartment(Long departmentId);

    Long getCountByDepartment(String departmentName);

    Long getCountByLimit(Long departmentId, String state);

    Long getCountByLimit(String departmentName, String state);

    Boolean setCartFree(Long id);

    Boolean setCartInventory(Long id);

    Boolean setCartEmergency(Long id);

    Map<String, String> getAllStates();

}

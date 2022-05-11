package com.daniel.cart.service;

import com.daniel.cart.domain.Cart;
import com.daniel.cart.domain.enums.CartExceptionEnum;
import com.daniel.cart.domain.res.CartBlockRes;
import com.daniel.cart.domain.res.CartRes;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    /**
     * 根据异常药品列表和药品的异常类型，找到 "所有" 的存在异常信息的急救车并存在list中
     * @param exceptionDrugMap 异常类型及其对应的异常药品列表
     * @return CartRes 中封装有 Cart 和 List<CartException>
     */
    List<CartRes> getException(Map<CartExceptionEnum, HashSet<Long>> exceptionDrugMap);

    /**
     * 根据异常药品列表和药品的异常类型，找到 "列表中" 的存在异常信息的急救车并存在list中
     * @param cartWithBlocks 目标急救车列表（带有 block 信息列表）
     * @param exceptionDrugMap 异常类型及其对应的异常药品列表
     * @return CartRes 中封装有 Cart 和 List<CartException>
     */
    List<CartRes> getException(List<CartBlockRes> cartWithBlocks, Map<CartExceptionEnum, HashSet<Long>> exceptionDrugMap);

        Boolean setCartFree(Long id);

    Boolean setCartInventory(Long id);

    Boolean setCartEmergency(Long id);

    Map<String, String> getAllStates();

}

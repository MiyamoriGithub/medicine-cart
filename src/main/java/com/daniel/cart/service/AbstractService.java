package com.daniel.cart.service;

import java.util.List;

/**
 * 抽象 Service 接口
 *
 * @author Daniel Zheng
 **/

public interface AbstractService<T> {

    List<T> findAll();

    List<T> findAll(Integer start, Integer pageSize);

    Long getCount();

    T findById(Long id);

    Boolean add(T t);

    Boolean modify(T t);

    Boolean remove(Long id);
}

package com.daniel.cart.controller;

import com.daniel.cart.domain.result.Result;

/**
 * Controller 抽象接口
 *
 * @author Daniel Zheng
 **/

public interface AbstractController {

    Result find(Long id, Integer start, Integer pageSize);
}

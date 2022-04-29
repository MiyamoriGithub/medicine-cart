package com.daniel.cart.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页请求 request 基类
 */
@Data
@Component
public class PageVo {
    private Integer start = 1;      // 当前页码
    private Integer pageSize;   // 每页信息条数
}

package com.daniel.cart.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页请求 request 基类
 */
@Data
@Component
public class PageVo {
    private Integer start = 1;      // 当前页码， 默认值为1
    private Integer pageSize;   // 每页信息条数，不设置默认值，否则每次查询均为分页查询

    public void setStart(Integer start) {
        if(start != null && start > 0) {
            this.start = start;
        }
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        }
    }
}

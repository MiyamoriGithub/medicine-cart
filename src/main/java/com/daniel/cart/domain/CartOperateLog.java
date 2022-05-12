package com.daniel.cart.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.daniel.cart.domain.enums.CartStateEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 急救车操作日志实体类
 *
 * @author Daniel Zheng
 **/

@Data
@Component
public class CartOperateLog {
    private Long cartOperateId;
    private Cart cart;
    private Long employeeId;
    private String employeeName;
    private Long departmentId;
    private String departmentName;
    private Timestamp operateTime;
    @JSONField(serialize = false)
    private CartStateEnum operateType;

    @JSONField(name = "operate")
    public String getOperate() {
        return this.operateType.getName();
    }
}

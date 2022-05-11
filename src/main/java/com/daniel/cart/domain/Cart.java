package com.daniel.cart.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.daniel.cart.domain.enums.CartStateEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.sql.Date;

@Component
@Getter
@Setter
public class Cart {
    private Long id;                // cart_id bigint primary key
    private Long departmentId;   // department_id int
    private String departmentName;
    private Date addTime;           // add_time timestamp not null
    @JSONField(serialize = false)
    private CartStateEnum state;           // status_type enum('free','inventory','emergency','unknown')
//    private Boolean isEnable;       // is_enable boolean default 1

    public Cart() {
        departmentName = "暂无";
    }

    public Cart(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Cart(Long id, Long departmentId, String departmentName, Date addTime, CartStateEnum state) {
        this.id = id;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.addTime = addTime;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", addTime=" + addTime +
                ", state=" + state +
                '}';
    }

    private void getDepartmentName(String name) {}

    @JSONField(name = "cartState")
    public String getCartState() {
        return this.state.getName();
    }
}

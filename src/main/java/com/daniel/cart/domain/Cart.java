package com.daniel.cart.domain;

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
    private CartStateEnum state;           // status_type enum('free','inventory','emergency','unknown')
    private Boolean isEnable;       // is_enable boolean default 1

    public Cart() {
        departmentName = "暂无";
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", addTime=" + addTime +
                ", state=" + state +
                ", isEnable=" + isEnable +
                '}';
    }

    private void getDepartmentName(String name) {}
}

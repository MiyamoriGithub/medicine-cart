package com.daniel.cart.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Department {
    private Long departmentId;            // department_id int primary key
    private String name;        // department_name varchar(50)
//    private Boolean isValid;    // is_enable default true

    public Department() {}
}

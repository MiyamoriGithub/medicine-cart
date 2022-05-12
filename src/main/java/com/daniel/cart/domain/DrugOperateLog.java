package com.daniel.cart.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 药品操作日志
 *
 * @author Daniel Zheng
 **/

@Data
@Component
public class DrugOperateLog {

    private Long drugOperateId;
    private Timestamp operateTime;
    private Drug drug;
    private Long blockId;
    private Grid grid;
    private Long cartId;
    private String operateType;
    private Long employeeId;
    private String employeeName;
    private Long departmentId;
    private String departmentName;

}
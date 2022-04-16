package com.daniel.cart.domain.vo;

public class DepartmentVo extends PageVo{
    private Long departmentId;

    private String departmentName;

    private Boolean isEnabled;

    public void setDepartmentId(Long id) {
        this.departmentId = id;
    }

    public void setDepartmentName(String name) {
        this.departmentName = name;
    }

    public void setIsEnabled(Boolean enabled) {
        this.isEnabled = enabled;
    }
}

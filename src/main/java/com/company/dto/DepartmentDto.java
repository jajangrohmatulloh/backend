package com.company.dto;

public class DepartmentDto {
    private String departmentcode;
    private String departmentname;

    public DepartmentDto() {}

    public DepartmentDto(String departmentcode, String departmentname) {
        this.departmentcode = departmentcode;
        this.departmentname = departmentname;
    }

    // Getters and Setters
    public String getDepartmentcode() {
        return departmentcode;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }
}
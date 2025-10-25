package com.company.dto;

import java.math.BigInteger;
import java.sql.Date;

public class EmployeeDto {
    private Integer empno;
    private String empname;
    private Integer tiercode;
    private String locationcode;
    private String departmentcode;
    private Integer supervisorcode;
    private BigInteger salary;
    private Date entrydate;

    public EmployeeDto() {}

    public EmployeeDto(Integer empno, String empname, Integer tiercode, String locationcode, 
                       String departmentcode, Integer supervisorcode, BigInteger salary, Date entrydate) {
        this.empno = empno;
        this.empname = empname;
        this.tiercode = tiercode;
        this.locationcode = locationcode;
        this.departmentcode = departmentcode;
        this.supervisorcode = supervisorcode;
        this.salary = salary;
        this.entrydate = entrydate;
    }

    // Getters and Setters
    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public Integer getTiercode() {
        return tiercode;
    }

    public void setTiercode(Integer tiercode) {
        this.tiercode = tiercode;
    }

    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }

    public String getDepartmentcode() {
        return departmentcode;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode;
    }

    public Integer getSupervisorcode() {
        return supervisorcode;
    }

    public void setSupervisorcode(Integer supervisorcode) {
        this.supervisorcode = supervisorcode;
    }

    public BigInteger getSalary() {
        return salary;
    }

    public void setSalary(BigInteger salary) {
        this.salary = salary;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }
}
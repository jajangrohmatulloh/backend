package com.company.dto.queries;

public class CumulativeSalaryDto {
    private String departmentCode;
    private Integer empno;
    private String empname;
    private Long cumulativeSalary;

    public CumulativeSalaryDto() {}

    public CumulativeSalaryDto(String departmentCode, Integer empno, String empname, Long cumulativeSalary) {
        this.departmentCode = departmentCode;
        this.empno = empno;
        this.empname = empname;
        this.cumulativeSalary = cumulativeSalary;
    }

    // Getters and setters
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

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

    public Long getCumulativeSalary() {
        return cumulativeSalary;
    }

    public void setCumulativeSalary(Long cumulativeSalary) {
        this.cumulativeSalary = cumulativeSalary;
    }
}
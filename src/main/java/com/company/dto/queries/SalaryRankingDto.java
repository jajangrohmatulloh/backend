package com.company.dto.queries;

public class SalaryRankingDto {
    private String locationName;
    private String departmentName;
    private String empname;
    private String tiername;
    private Long salary;
    private Integer rank;
    private Long salaryGap;

    public SalaryRankingDto() {}

    public SalaryRankingDto(String locationName, String departmentName, String empname, String tiername, Long salary, Integer rank, Long salaryGap) {
        this.locationName = locationName;
        this.departmentName = departmentName;
        this.empname = empname;
        this.tiername = tiername;
        this.salary = salary;
        this.rank = rank;
        this.salaryGap = salaryGap;
    }

    // Getters and setters
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getTiername() {
        return tiername;
    }

    public void setTiername(String tiername) {
        this.tiername = tiername;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getSalaryGap() {
        return salaryGap;
    }

    public void setSalaryGap(Long salaryGap) {
        this.salaryGap = salaryGap;
    }
}
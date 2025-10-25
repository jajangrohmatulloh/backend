package com.company.dto.queries;

public class DepartmentAnalysisDto {
    private String locationName;
    private String deptWithMostEmployees;
    private Integer deptEmployeeCount;
    private Double avgSalaryOfLowestDept;

    public DepartmentAnalysisDto() {}

    public DepartmentAnalysisDto(String locationName, String deptWithMostEmployees, Integer deptEmployeeCount, Double avgSalaryOfLowestDept) {
        this.locationName = locationName;
        this.deptWithMostEmployees = deptWithMostEmployees;
        this.deptEmployeeCount = deptEmployeeCount;
        this.avgSalaryOfLowestDept = avgSalaryOfLowestDept;
    }

    // Getters and setters
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDeptWithMostEmployees() {
        return deptWithMostEmployees;
    }

    public void setDeptWithMostEmployees(String deptWithMostEmployees) {
        this.deptWithMostEmployees = deptWithMostEmployees;
    }

    public Integer getDeptEmployeeCount() {
        return deptEmployeeCount;
    }

    public void setDeptEmployeeCount(Integer deptEmployeeCount) {
        this.deptEmployeeCount = deptEmployeeCount;
    }

    public Double getAvgSalaryOfLowestDept() {
        return avgSalaryOfLowestDept;
    }

    public void setAvgSalaryOfLowestDept(Double avgSalaryOfLowestDept) {
        this.avgSalaryOfLowestDept = avgSalaryOfLowestDept;
    }
}
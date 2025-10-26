package com.company.service;

import com.company.dao.EmployeeDao;
import com.company.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public List<EmployeeDto> findAll() {
        return employeeDao.findAll();
    }

    public EmployeeDto findById(Integer empno) {
        return employeeDao.findById(empno);
    }

    public EmployeeDto save(EmployeeDto employee) {
        // Validate input
        if (employee.getEmpno() == null) {
            throw new IllegalArgumentException("Employee number is required");
        }
        if (employee.getEmpname() == null || employee.getEmpname().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name is required");
        }
        return employeeDao.save(employee);
    }

    public EmployeeDto update(Integer empno, EmployeeDto employee) {
        // Validate input
        if (employee.getEmpname() == null || employee.getEmpname().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name is required");
        }
        return employeeDao.update(empno, employee);
    }

    public void delete(Integer empno) {
        employeeDao.delete(empno);
    }
    
    public List<EmployeeDto> searchEmployees(String searchTerm, Map<String, Object> filters) {
        return employeeDao.searchEmployees(searchTerm, filters);
    }
}
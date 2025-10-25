package com.company.service;

import com.company.dao.DepartmentDao;
import com.company.dto.DepartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public List<DepartmentDto> findAll() {
        return departmentDao.findAll();
    }

    public DepartmentDto findById(String departmentcode) {
        return departmentDao.findById(departmentcode);
    }

    public DepartmentDto save(DepartmentDto department) {
        // Validate input
        if (department.getDepartmentcode() == null || department.getDepartmentcode().trim().isEmpty()) {
            throw new IllegalArgumentException("Department code is required");
        }
        if (department.getDepartmentname() == null || department.getDepartmentname().trim().isEmpty()) {
            throw new IllegalArgumentException("Department name is required");
        }
        return departmentDao.save(department);
    }

    public DepartmentDto update(String departmentcode, DepartmentDto department) {
        // Validate input
        if (department.getDepartmentname() == null || department.getDepartmentname().trim().isEmpty()) {
            throw new IllegalArgumentException("Department name is required");
        }
        return departmentDao.update(departmentcode, department);
    }

    public void delete(String departmentcode) {
        departmentDao.delete(departmentcode);
    }
}
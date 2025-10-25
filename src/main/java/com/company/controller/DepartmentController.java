package com.company.controller;

import com.company.dto.DepartmentDto;
import com.company.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Note: Basic pagination implementation, could be enhanced
        List<DepartmentDto> departments = departmentService.findAll();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{departmentcode}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable String departmentcode) {
        DepartmentDto department = departmentService.findById(departmentcode);
        if (department != null) {
            return ResponseEntity.ok(department);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto department) {
        try {
            DepartmentDto createdDepartment = departmentService.save(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{departmentcode}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @PathVariable String departmentcode,
            @Valid @RequestBody DepartmentDto department) {
        try {
            DepartmentDto updatedDepartment = departmentService.update(departmentcode, department);
            if (updatedDepartment != null) {
                return ResponseEntity.ok(updatedDepartment);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{departmentcode}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String departmentcode) {
        departmentService.delete(departmentcode);
        return ResponseEntity.noContent().build();
    }
}
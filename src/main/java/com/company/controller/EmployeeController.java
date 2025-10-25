package com.company.controller;

import com.company.dto.EmployeeDto;
import com.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Note: Basic pagination implementation, could be enhanced
        List<EmployeeDto> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{empno}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Integer empno) {
        EmployeeDto employee = employeeService.findById(empno);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employee) {
        try {
            EmployeeDto createdEmployee = employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{empno}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Integer empno,
            @Valid @RequestBody EmployeeDto employee) {
        try {
            EmployeeDto updatedEmployee = employeeService.update(empno, employee);
            if (updatedEmployee != null) {
                return ResponseEntity.ok(updatedEmployee);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{empno}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer empno) {
        employeeService.delete(empno);
        return ResponseEntity.noContent().build();
    }
}
package com.company.controller;

import com.company.dto.EmployeeDto;
import com.company.dto.SearchRequest;
import com.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

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
    
    @PostMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(@RequestBody SearchRequest searchRequest) {
        List<EmployeeDto> employees = employeeService.searchEmployees(
            searchRequest.getSearchTerm(), 
            searchRequest.getFilters()
        );
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployeesWithParams(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "empname") String empname,
            @RequestParam(required = false, name = "empno") Integer empno,
            @RequestParam(required = false, name = "tiercode") Integer tiercode,
            @RequestParam(required = false, name = "locationcode") String locationcode,
            @RequestParam(required = false, name = "departmentcode") String departmentcode,
            @RequestParam(required = false, name = "supervisorcode") Integer supervisorcode,
            @RequestParam(required = false, name = "minSalary") Long minSalary,
            @RequestParam(required = false, name = "maxSalary") Long maxSalary,
            @RequestParam(required = false, name = "minEntryDate") String minEntryDate,
            @RequestParam(required = false, name = "maxEntryDate") String maxEntryDate) {
        
        // Build filters map
        Map<String, Object> filters = new java.util.HashMap<>();
        if (empname != null) filters.put("empname", empname);
        if (empno != null) filters.put("empno", empno);
        if (tiercode != null) filters.put("tiercode", tiercode);
        if (locationcode != null) filters.put("locationcode", locationcode);
        if (departmentcode != null) filters.put("departmentcode", departmentcode);
        if (supervisorcode != null) filters.put("supervisorcode", supervisorcode);
        if (minSalary != null) filters.put("minSalary", minSalary);
        if (maxSalary != null) filters.put("maxSalary", maxSalary);
        if (minEntryDate != null) filters.put("minEntryDate", minEntryDate);
        if (maxEntryDate != null) filters.put("maxEntryDate", maxEntryDate);
        
        List<EmployeeDto> employees = employeeService.searchEmployees(searchTerm, filters);
        return ResponseEntity.ok(employees);
    }
}
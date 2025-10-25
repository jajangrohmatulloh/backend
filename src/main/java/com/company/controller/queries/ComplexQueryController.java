package com.company.controller.queries;

import com.company.dto.queries.CumulativeSalaryDto;
import com.company.dto.queries.DepartmentAnalysisDto;
import com.company.dto.queries.SalaryRankingDto;
import com.company.service.queries.CumulativeSalaryService;
import com.company.service.queries.DepartmentAnalysisService;
import com.company.service.queries.SalaryRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queries")
public class ComplexQueryController {

    @Autowired
    private CumulativeSalaryService cumulativeSalaryService;
    
    @Autowired
    private DepartmentAnalysisService departmentAnalysisService;
    
    @Autowired
    private SalaryRankingService salaryRankingService;

    @GetMapping("/cumulative-salary")
    public ResponseEntity<List<CumulativeSalaryDto>> getCumulativeSalary() {
        List<CumulativeSalaryDto> result = cumulativeSalaryService.getCumulativeSalary();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/department-analysis")
    public ResponseEntity<List<DepartmentAnalysisDto>> getDepartmentAnalysis() {
        List<DepartmentAnalysisDto> result = departmentAnalysisService.getDepartmentAnalysis();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/salary-ranking")
    public ResponseEntity<List<SalaryRankingDto>> getSalaryRanking() {
        List<SalaryRankingDto> result = salaryRankingService.getSalaryRanking();
        return ResponseEntity.ok(result);
    }
}
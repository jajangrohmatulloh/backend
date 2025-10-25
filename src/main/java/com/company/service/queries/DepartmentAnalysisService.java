package com.company.service.queries;

import com.company.dao.queries.DepartmentAnalysisDao;
import com.company.dto.queries.DepartmentAnalysisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentAnalysisService {

    @Autowired
    private DepartmentAnalysisDao departmentAnalysisDao;

    public List<DepartmentAnalysisDto> getDepartmentAnalysis() {
        return departmentAnalysisDao.getDepartmentAnalysis();
    }
}
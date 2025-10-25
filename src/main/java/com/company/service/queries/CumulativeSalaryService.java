package com.company.service.queries;

import com.company.dao.queries.CumulativeSalaryDao;
import com.company.dto.queries.CumulativeSalaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CumulativeSalaryService {

    @Autowired
    private CumulativeSalaryDao cumulativeSalaryDao;

    public List<CumulativeSalaryDto> getCumulativeSalary() {
        return cumulativeSalaryDao.getCumulativeSalary();
    }
}
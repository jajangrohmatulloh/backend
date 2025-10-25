package com.company.service.queries;

import com.company.dao.queries.SalaryRankingDao;
import com.company.dto.queries.SalaryRankingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryRankingService {

    @Autowired
    private SalaryRankingDao salaryRankingDao;

    public List<SalaryRankingDto> getSalaryRanking() {
        return salaryRankingDao.getSalaryRanking();
    }
}
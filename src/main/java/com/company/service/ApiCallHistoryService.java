package com.company.service;

import com.company.dao.ApiCallHistoryDao;
import com.company.dto.ApiCallHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApiCallHistoryService {

    @Autowired
    private ApiCallHistoryDao apiCallHistoryDao;

    public void logApiCall(String endpoint, String httpMethod, String userIdentifier, Integer responseStatus) {
        System.out.println("Attempting to log API call: " + endpoint + " " + httpMethod + " " + responseStatus);
        ApiCallHistoryDto history = new ApiCallHistoryDto(
            LocalDateTime.now(),
            endpoint,
            httpMethod,
            userIdentifier,
            responseStatus
        );
        try {
            apiCallHistoryDao.save(history);
            System.out.println("Successfully logged API call to database");
        } catch (Exception e) {
            System.err.println("Failed to log API call: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ApiCallHistoryDto> getRecentLogs() {
        return apiCallHistoryDao.findAll();
    }
}
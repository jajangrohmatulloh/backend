package com.company.controller;

import com.company.dto.ApiCallHistoryDto;
import com.company.service.ApiCallHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class ApiCallHistoryController {

    @Autowired
    private ApiCallHistoryService apiCallHistoryService;

    @GetMapping
    public ResponseEntity<List<ApiCallHistoryDto>> getApiCallHistory() {
        List<ApiCallHistoryDto> history = apiCallHistoryService.getRecentLogs();
        return ResponseEntity.ok(history);
    }
}
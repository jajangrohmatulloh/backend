package com.company.event;

import com.company.service.ApiCallHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ApiCallEventListener {

    @Autowired
    private ApiCallHistoryService apiCallHistoryService;

    @Async
    @EventListener
    public void handleApiCallEvent(ApiCallEvent event) {
        try {
            apiCallHistoryService.logApiCall(
                event.getEndpoint(),
                event.getHttpMethod(), 
                event.getUserIdentifier(),
                event.getResponseStatus()
            );
        } catch (Exception e) {
            // Log the error but don't impact the main API call
            System.err.println("Error logging API call: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
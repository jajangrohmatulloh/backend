package com.company.dto;

import java.time.LocalDateTime;

public class ApiCallHistoryDto {
    private Long id;
    private LocalDateTime timestamp;
    private String endpoint;
    private String httpMethod;
    private String userIdentifier;
    private Integer responseStatus;

    public ApiCallHistoryDto() {}

    public ApiCallHistoryDto(LocalDateTime timestamp, String endpoint, String httpMethod, 
                             String userIdentifier, Integer responseStatus) {
        this.timestamp = timestamp;
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
        this.userIdentifier = userIdentifier;
        this.responseStatus = responseStatus;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }
}
package com.company.event;

import org.springframework.context.ApplicationEvent;

public class ApiCallEvent extends ApplicationEvent {
    private final String endpoint;
    private final String httpMethod;
    private final String userIdentifier;
    private final Integer responseStatus;

    public ApiCallEvent(Object source, String endpoint, String httpMethod, String userIdentifier, Integer responseStatus) {
        super(source);
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
        this.userIdentifier = userIdentifier;
        this.responseStatus = responseStatus;
    }

    // Getters
    public String getEndpoint() {
        return endpoint;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }
}
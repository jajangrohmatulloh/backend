package com.company.interceptor;

import com.company.event.ApiCallEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ApiCallLoggingInterceptor implements HandlerInterceptor {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Store the start time in the request attributes for later use
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Publish an event to log the API call - this will be handled asynchronously
        if (request.getRequestURI().startsWith("/api")) {
            String endpoint = request.getRequestURI();
            String httpMethod = request.getMethod();
            
            // For now, we don't have user authentication, so user identifier will be null
            String userIdentifier = null; // In a real application, this would come from authentication
            
            Integer responseStatus = response.getStatus();
            
            ApiCallEvent event = new ApiCallEvent(this, endpoint, httpMethod, userIdentifier, responseStatus);
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // We already published the event in postHandle
    }
}
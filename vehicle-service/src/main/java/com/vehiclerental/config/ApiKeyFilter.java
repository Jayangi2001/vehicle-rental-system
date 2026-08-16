package com.vehiclerental.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    
    private static final String VALID_API_KEY = "VEHICLE_SERVICE_SECRET_KEY_123";

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String path = request.getRequestURI();

    // Allow Swagger UI & OpenAPI endpoints without API key check
    if (path.contains("/swagger-ui") || path.contains("/v3/api-docs")) {
        filterChain.doFilter(request, response);
        return;
    }

    // Check for API Key on all other microservice endpoints
    String requestApiKey = request.getHeader(API_KEY_HEADER);

    if (VALID_API_KEY.equals(requestApiKey)) {
        filterChain.doFilter(request, response);
    } else {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Invalid or Missing API Key");
    }
}
}
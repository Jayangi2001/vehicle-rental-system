package com.vehiclerental.vehicle_service.config;

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
    // Assignment එකේ test කරන්න භාවිතා කරන Secret API Key එක
    private static final String VALID_API_KEY = "VEHICLE_SERVICE_SECRET_KEY_123";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Swagger documentation URLs වලට API Key එක නැතුව access වෙන්න ඉඩ දීම
        String path = request.getRequestURI();
        if (path.contains("/swagger-ui") || path.contains("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestApiKey = request.getHeader(API_KEY_HEADER);

        // API Key එක නිවැරදිදැයි පරීක්ෂා කිරීම
        if (VALID_API_KEY.equals(requestApiKey)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid or Missing API Key");
        }
    }
}

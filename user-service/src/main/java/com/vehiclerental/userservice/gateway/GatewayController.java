package com.vehiclerental.userservice.gateway;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class GatewayController {

    private final RestTemplate restTemplate;

    @Value("${services.vehicle-service.url}")
    private String vehicleServiceUrl;

    @Value("${services.rental-payment-service.url}")
    private String rentalServiceUrl;

    @RequestMapping("/vehicles/**")
    public ResponseEntity<?> routeToVehicleService(HttpServletRequest request,
                                                     @RequestBody(required = false) String body) {
        return forward(vehicleServiceUrl, request, body);
    }

    @RequestMapping("/rentals/**")
    public ResponseEntity<?> routeToRentalService(HttpServletRequest request,
                                                    @RequestBody(required = false) String body) {
        return forward(rentalServiceUrl, request, body);
    }

    @RequestMapping("/payments/**")
    public ResponseEntity<?> routeToPaymentService(HttpServletRequest request,
                                                     @RequestBody(required = false) String body) {
        return forward(rentalServiceUrl, request, body);
    }

    private ResponseEntity<?> forward(String targetBaseUrl, HttpServletRequest request, String body) {
    
        String targetUrl = targetBaseUrl + request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        Object email = request.getAttribute("authenticatedEmail");
        if (email != null) {
            headers.set("X-User-Email", email.toString());
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(targetUrl, method, entity, String.class);
        } catch (HttpClientErrorException ex) {
            
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
    }
}

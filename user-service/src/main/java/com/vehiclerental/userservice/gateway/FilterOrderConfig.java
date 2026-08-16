package com.vehiclerental.userservice.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterOrderConfig {

    @Value("${rate-limit.capacity}")
    private int capacity;

    @Value("${rate-limit.refill-tokens}")
    private int refillTokens;

    @Value("${rate-limit.refill-duration-seconds}")
    private int refillDurationSeconds;

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistration() {
        RateLimitFilter filter = new RateLimitFilter(capacity, refillTokens, refillDurationSeconds);
        FilterRegistrationBean<RateLimitFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}

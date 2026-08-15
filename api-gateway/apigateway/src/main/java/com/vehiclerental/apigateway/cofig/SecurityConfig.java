package main.java.com.vehiclerental.apigateway.cofig; 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()) // CSRF protection එක disable කිරීම (API gateways සඳහා සාමාන්‍යයෙන් මෙය සිදු කෙරේ)
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/auth/**").permitAll() // /auth/** යටතේ ඇති endpoints වලට (Register/Login) කවුරුත් block නොවී යාමට ඉඩ ලබා දීම
                .anyExchange().authenticated()        // වෙනත් ඕනෑම ඉල්ලීමක් සඳහා Authentication (Token) අවශ්‍ය වීම
            );
        return http.build();
    }
}
package com.example.userauthservice.controller;

import com.example.userauthservice.model.User;
import com.example.userauthservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 1. Register Endpoint (/auth/register)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // 2. Login Endpoint (/auth/login)
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginRequest) {
        Map<String, String> response = new HashMap<>();
        
        Optional<User> existingUser = userRepository.findByUsername(loginRequest.getUsername());

        if (existingUser.isPresent() && existingUser.get().getPassword().equals(loginRequest.getPassword())) {
           
            response.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.sample-token");
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }
}
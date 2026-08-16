package com.vehiclerental.userservice.auth;

import com.vehiclerental.userservice.auth.dto.AuthResponse;
import com.vehiclerental.userservice.auth.dto.LoginRequest;
import com.vehiclerental.userservice.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login, and profile endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user account")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        User created = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id", created.getId(),
                "name", created.getName(),
                "email", created.getEmail()
        ));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and receive an access token")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Get the profile of the currently authenticated user")
    public ResponseEntity<?> me(@RequestAttribute("authenticatedEmail") String email) {
        User user = authService.getByEmail(email);
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail()
        ));
    }
}

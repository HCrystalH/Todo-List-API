package com.toDoListAPI.project.controller;

import com.toDoListAPI.project.dto.JwtResponse;
import com.toDoListAPI.project.dto.LoginRequest;
import com.toDoListAPI.project.dto.RegisterRequest;
import com.toDoListAPI.project.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")     // prefix
public class AuthController {
    private final AuthService authService;

    // Constructor Injection
    public AuthController(AuthService authService) {
        // Inject authService into AuthController
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request){
        JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

package com._Rbrothers.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._Rbrothers.user_service.dto.LoginRequest;
import com._Rbrothers.user_service.dto.LoginResponse;
import com._Rbrothers.user_service.dto.RefreshRequest;
import com._Rbrothers.user_service.dto.RegisterRequest;
import com._Rbrothers.user_service.dto.UserResponse;
import com._Rbrothers.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @RequestBody RefreshRequest request) {

        return ResponseEntity.ok(
                userService.refreshToken(request.getRefreshToken())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/test")
    public String testRateLimiter(){
        return "Hello, Working up!";
    }
}

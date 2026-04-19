package com.omniverse.auth_Service.controller;

import com.omniverse.auth_Service.dto.LoginRequest;
import com.omniverse.auth_Service.dto.LoginResponse;
import com.omniverse.auth_Service.dto.RegisterRequest;
import com.omniverse.auth_Service.dto.RegisterResponse;
import com.omniverse.auth_Service.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterResponse  register(@Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        logger.info("Login attempt for email: {}", request.getEmail());

        return authService.login(request);
    }
}

package com.omniverse.auth_Service.service;

import com.omniverse.auth_Service.dto.LoginRequest;
import com.omniverse.auth_Service.dto.LoginResponse;
import com.omniverse.auth_Service.dto.RegisterRequest;
import com.omniverse.auth_Service.dto.RegisterResponse;
import com.omniverse.auth_Service.entity.User;
import com.omniverse.auth_Service.repository.UserRepository;
import com.omniverse.auth_Service.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // 1. Convert DTO → Entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(hashedPassword);

        // 2. Save to DB
        userRepository.save(user);

        logger.info("User registered: {}", user.getEmail());

        // 3. Response
        RegisterResponse response = new RegisterResponse();
        response.setMessage("User registered successfully");
        response.setUsername(user.getUsername());

        return response;
    }

    public LoginResponse login(LoginRequest request) {

        logger.debug("Processing login for: {}", request.getEmail());

        // 1. Fetch user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Verify password
        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!isMatch) {
            logger.warn("Invalid password attempt for email: {}", request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }

        // 3. Success
        logger.info("User logged in successfully: {}", user.getEmail());

        String token = jwtService.generateToken(user.getId(), user.getEmail());

        //4.  Generate JWT
        LoginResponse response = new LoginResponse();
        response.setMessage("Login successful");
        response.setUsername(user.getUsername());
        response.setToken(token);

        return response;
    }
}

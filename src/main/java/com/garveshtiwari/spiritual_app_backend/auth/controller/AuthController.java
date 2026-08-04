package com.garveshtiwari.spiritual_app_backend.auth.controller;

import com.garveshtiwari.spiritual_app_backend.auth.dto.LoginRequest;
import com.garveshtiwari.spiritual_app_backend.auth.dto.LoginResponse;
import com.garveshtiwari.spiritual_app_backend.auth.dto.RefreshTokenRequest;
import com.garveshtiwari.spiritual_app_backend.auth.dto.RefreshTokenResponse;
import com.garveshtiwari.spiritual_app_backend.auth.dto.RegisterRequest;
import com.garveshtiwari.spiritual_app_backend.auth.dto.RegisterResponse;
import com.garveshtiwari.spiritual_app_backend.auth.service.AuthenticationService;
import com.garveshtiwari.spiritual_app_backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        RegisterResponse response = userService.register(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        LoginResponse response = authenticationService.login(
                request
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {

        RefreshTokenResponse response =
                authenticationService.refreshToken(
                        request
                );

        return ResponseEntity.ok(response);
    }
}
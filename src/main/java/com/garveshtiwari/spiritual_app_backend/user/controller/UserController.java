package com.garveshtiwari.spiritual_app_backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                "Authenticated user: " + email
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {

        return ResponseEntity.ok(
                "Welcome, admin."
        );
    }
}
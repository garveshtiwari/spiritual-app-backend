package com.garveshtiwari.spiritual_app_backend.user.controller;

import com.garveshtiwari.spiritual_app_backend.user.dto.UserProfileResponse;
import com.garveshtiwari.spiritual_app_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(
            Authentication authentication
    ) {

        UserProfileResponse response = userService.getCurrentUser(
                authentication.getName()
        );

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {

        return ResponseEntity.ok(
                "Welcome, admin."
        );
    }
}
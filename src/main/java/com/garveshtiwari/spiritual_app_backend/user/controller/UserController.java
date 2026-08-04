package com.garveshtiwari.spiritual_app_backend.user.controller;

import com.garveshtiwari.spiritual_app_backend.user.dto.UpdateProfileRequest;
import com.garveshtiwari.spiritual_app_backend.user.dto.UserProfileResponse;
import com.garveshtiwari.spiritual_app_backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request
    ) {

        UserProfileResponse response = userService.updateProfile(
                authentication.getName(),
                request
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
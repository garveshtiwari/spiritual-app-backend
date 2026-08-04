package com.garveshtiwari.spiritual_app_backend.auth.service;

import com.garveshtiwari.spiritual_app_backend.auth.dto.*;
import com.garveshtiwari.spiritual_app_backend.auth.entity.RefreshToken;
import com.garveshtiwari.spiritual_app_backend.common.security.JwtService;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl
        implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new RuntimeException(
                                "Invalid credentials."
                        )
                );

        boolean isPasswordCorrect = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!isPasswordCorrect) {
            throw new RuntimeException(
                    "Invalid credentials."
            );
        }

        String accessToken = jwtService.generateToken(
                user.getEmail()
        );

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(
                        user
                );

        return new LoginResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    @Override
    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    ) {

        RefreshToken refreshToken =
                refreshTokenService.verifyExpiration(
                        request.getRefreshToken()
                );

        String accessToken = jwtService.generateToken(
                refreshToken.getUser().getEmail()
        );

        return new RefreshTokenResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    @Override
    public LogoutResponse logout() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found."
                        )
                );

        refreshTokenService.deleteByUser(
                user.getId()
        );

        return new LogoutResponse(
                "Logged out successfully."
        );
    }
}
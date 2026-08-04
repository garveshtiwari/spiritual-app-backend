package com.garveshtiwari.spiritual_app_backend.auth.service;

import com.garveshtiwari.spiritual_app_backend.auth.dto.LoginRequest;
import com.garveshtiwari.spiritual_app_backend.auth.dto.LoginResponse;
import com.garveshtiwari.spiritual_app_backend.auth.dto.LogoutResponse;
import com.garveshtiwari.spiritual_app_backend.auth.dto.RefreshTokenRequest;
import com.garveshtiwari.spiritual_app_backend.auth.dto.RefreshTokenResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    );

    LogoutResponse logout();
}
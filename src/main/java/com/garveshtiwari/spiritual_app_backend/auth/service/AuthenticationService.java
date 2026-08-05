package com.garveshtiwari.spiritual_app_backend.auth.service;

import com.garveshtiwari.spiritual_app_backend.auth.dto.*;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    );

    LogoutResponse logout();

    void forgotPassword(
            ForgotPasswordRequest request
    );

    void verifyOtp(
            VerifyOtpRequest request
    );

    void resetPassword(
            ResetPasswordRequest request
    );
}
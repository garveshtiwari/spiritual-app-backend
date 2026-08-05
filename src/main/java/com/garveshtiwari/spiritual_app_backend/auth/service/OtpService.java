package com.garveshtiwari.spiritual_app_backend.auth.service;

public interface OtpService {

    void generateOtp(String email);

    void verifyOtp(
            String email,
            String otp
    );

    void resetPassword(
            String email,
            String newPassword
    );
}
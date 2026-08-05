package com.garveshtiwari.spiritual_app_backend.auth.service;

public interface EmailService {

    void sendOtp(
            String recipientEmail,
            String otp
    );
}
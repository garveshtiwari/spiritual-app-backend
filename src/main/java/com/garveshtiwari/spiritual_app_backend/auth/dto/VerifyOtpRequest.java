package com.garveshtiwari.spiritual_app_backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {

    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "OTP is required.")
    private String otp;
}
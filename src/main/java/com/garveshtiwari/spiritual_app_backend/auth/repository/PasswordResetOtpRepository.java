package com.garveshtiwari.spiritual_app_backend.auth.repository;

import com.garveshtiwari.spiritual_app_backend.auth.entity.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetOtpRepository
        extends JpaRepository<PasswordResetOtp, Long> {

    Optional<PasswordResetOtp> findByOtp(
            String otp
    );

    Optional<PasswordResetOtp> findByUser_Email(
            String email
    );

    Optional<PasswordResetOtp> findByUser_EmailAndOtp(
            String email,
            String otp
    );

    void deleteByUserId(
            Long userId
    );

    void deleteByExpiryTimeBefore(
            LocalDateTime dateTime
    );
}
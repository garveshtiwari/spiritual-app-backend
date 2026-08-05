package com.garveshtiwari.spiritual_app_backend.auth.service;

import com.garveshtiwari.spiritual_app_backend.auth.repository.PasswordResetOtpRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpCleanupServiceImpl
        implements OtpCleanupService {

    private final PasswordResetOtpRepository otpRepository;

    @Override
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void deleteExpiredOtps() {

        otpRepository.deleteByExpiryTimeBefore(
                LocalDateTime.now()
        );
    }
}
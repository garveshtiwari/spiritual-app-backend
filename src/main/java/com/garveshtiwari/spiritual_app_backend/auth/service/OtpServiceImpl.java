package com.garveshtiwari.spiritual_app_backend.auth.service;

import com.garveshtiwari.spiritual_app_backend.auth.entity.PasswordResetOtp;
import com.garveshtiwari.spiritual_app_backend.auth.repository.PasswordResetOtpRepository;
import com.garveshtiwari.spiritual_app_backend.common.exception.BadRequestException;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend.common.util.OtpGenerator;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private static final long OTP_EXPIRATION_TIME = 5;

    private static final long OTP_REQUEST_COOLDOWN = 1;

    private static final int MAX_ATTEMPTS = 5;

    private final UserRepository userRepository;

    private final PasswordResetOtpRepository otpRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Override
    public void generateOtp(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found."
                        )
                );

        Optional<PasswordResetOtp> existingOtp =
                otpRepository.findByUser_Email(email);

        if (existingOtp.isPresent()) {

            PasswordResetOtp otp = existingOtp.get();

            if (otp.getCreatedAt()
                    .plusMinutes(OTP_REQUEST_COOLDOWN)
                    .isAfter(LocalDateTime.now())) {

                throw new BadRequestException(
                        "Please wait before requesting another OTP."
                );
            }

            otpRepository.delete(otp);
        }

        String otp = OtpGenerator.generateOtp();

        PasswordResetOtp passwordResetOtp =
                PasswordResetOtp.builder()
                        .otp(
                                passwordEncoder.encode(
                                        otp
                                )
                        )
                        .user(user)
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .expiryTime(
                                LocalDateTime.now()
                                        .plusMinutes(
                                                OTP_EXPIRATION_TIME
                                        )
                        )
                        .attemptCount(0)
                        .verified(false)
                        .build();

        otpRepository.save(
                passwordResetOtp
        );

        emailService.sendOtp(
                user.getEmail(),
                otp
        );
    }

    @Override
    public void verifyOtp(
            String email,
            String otp
    ) {

        PasswordResetOtp passwordResetOtp =
                otpRepository
                        .findByUser_Email(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "OTP not found."
                                )
                        );

        if (passwordResetOtp.getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            otpRepository.delete(
                    passwordResetOtp
            );

            throw new BadRequestException(
                    "OTP has expired."
            );
        }

        if (passwordResetOtp.getAttemptCount()
                >= MAX_ATTEMPTS) {

            otpRepository.delete(
                    passwordResetOtp
            );

            throw new BadRequestException(
                    "Maximum attempts exceeded."
            );
        }

        boolean matches =
                passwordEncoder.matches(
                        otp,
                        passwordResetOtp.getOtp()
                );

        if (!matches) {

            passwordResetOtp.setAttemptCount(
                    passwordResetOtp.getAttemptCount() + 1
            );

            otpRepository.save(
                    passwordResetOtp
            );

            throw new BadRequestException(
                    "Invalid OTP."
            );
        }

        passwordResetOtp.setAttemptCount(
                0
        );

        passwordResetOtp.setVerified(
                true
        );

        otpRepository.save(
                passwordResetOtp
        );
    }

    @Override
    public void resetPassword(
            String email,
            String newPassword
    ) {

        PasswordResetOtp passwordResetOtp =
                otpRepository
                        .findByUser_Email(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "OTP not found."
                                )
                        );

        if (passwordResetOtp.getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            otpRepository.delete(
                    passwordResetOtp
            );

            throw new BadRequestException(
                    "OTP has expired."
            );
        }

        if (!passwordResetOtp.isVerified()) {

            throw new BadRequestException(
                    "OTP has not been verified."
            );
        }

        User user = passwordResetOtp.getUser();

        user.setPassword(
                passwordEncoder.encode(
                        newPassword
                )
        );

        user.setUpdatedAt(
                LocalDateTime.now()
        );

        userRepository.save(
                user
        );

        otpRepository.delete(
                passwordResetOtp
        );
    }
}
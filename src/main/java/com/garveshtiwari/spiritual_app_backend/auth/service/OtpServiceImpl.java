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

@Service
@Transactional
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private static final long OTP_EXPIRATION_TIME = 5;

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

        otpRepository.deleteByUserId(
                user.getId()
        );

        String otp = OtpGenerator.generateOtp();

        PasswordResetOtp passwordResetOtp =
                PasswordResetOtp.builder()
                        .otp(otp)
                        .user(user)
                        .expiryTime(
                                LocalDateTime.now()
                                        .plusMinutes(
                                                OTP_EXPIRATION_TIME
                                        )
                        )
                        .verified(false)
                        .build();

        otpRepository.save(passwordResetOtp);

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
                        .findByUser_EmailAndOtp(
                                email,
                                otp
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Invalid email or OTP."
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

        userRepository.save(user);

        otpRepository.delete(passwordResetOtp);
    }
}
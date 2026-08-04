package com.garveshtiwari.spiritual_app_backend.auth.service;

import com.garveshtiwari.spiritual_app_backend.auth.entity.RefreshToken;
import com.garveshtiwari.spiritual_app_backend.auth.repository.RefreshTokenRepository;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private static final long REFRESH_TOKEN_DURATION =
            7L * 24 * 60 * 60;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(User user) {

        refreshTokenRepository.deleteByUserId(
                user.getId()
        );

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(
                        LocalDateTime.now()
                                .plusSeconds(
                                        REFRESH_TOKEN_DURATION
                                )
                )
                .build();

        return refreshTokenRepository.save(
                refreshToken
        );
    }

    @Override
    public RefreshToken verifyExpiration(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Refresh token not found."
                                )
                        );

        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(
                    refreshToken
            );

            throw new RuntimeException(
                    "Refresh token expired."
            );
        }

        return refreshToken;
    }

    @Override
    public void deleteByUser(Long userId) {

        refreshTokenRepository.deleteByUserId(
                userId
        );
    }

    @Override
    public RefreshToken getByToken(String token) {

        return refreshTokenRepository
                .findByToken(token)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Refresh token not found."
                        )
                );
    }
}
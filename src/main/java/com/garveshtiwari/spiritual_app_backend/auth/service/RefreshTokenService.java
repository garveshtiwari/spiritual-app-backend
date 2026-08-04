package com.garveshtiwari.spiritual_app_backend.auth.service;

import com.garveshtiwari.spiritual_app_backend.auth.entity.RefreshToken;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyExpiration(String token);

    void deleteByUser(Long userId);

    RefreshToken getByToken(String token);
}
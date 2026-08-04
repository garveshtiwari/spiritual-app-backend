package com.garveshtiwari.spiritual_app_backend.user.service;

import com.garveshtiwari.spiritual_app_backend.auth.dto.RegisterRequest;
import com.garveshtiwari.spiritual_app_backend.auth.dto.RegisterResponse;
import com.garveshtiwari.spiritual_app_backend.user.dto.UserProfileResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

    UserProfileResponse getCurrentUser(String email);

}
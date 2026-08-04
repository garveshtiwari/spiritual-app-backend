package com.garveshtiwari.spiritual_app_backend.user.service;

import com.garveshtiwari.spiritual_app_backend.auth.dto.RegisterRequest;
import com.garveshtiwari.spiritual_app_backend.auth.dto.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

}
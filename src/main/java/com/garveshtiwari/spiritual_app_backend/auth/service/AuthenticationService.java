package com.garveshtiwari.spiritual_app_backend.auth.service;

import com.garveshtiwari.spiritual_app_backend.auth.dto.LoginRequest;
import com.garveshtiwari.spiritual_app_backend.auth.dto.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

}
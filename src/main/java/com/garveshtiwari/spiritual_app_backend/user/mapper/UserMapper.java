package com.garveshtiwari.spiritual_app_backend.user.mapper;

import com.garveshtiwari.spiritual_app_backend.user.dto.UserProfileResponse;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserProfileResponse toProfileResponse(
            User user
    ) {

        return UserProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
package com.garveshtiwari.spiritual_app_backend.common.response;

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null);
    }
}

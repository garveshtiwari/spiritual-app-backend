package com.garveshtiwari.spiritual_app_backend
        .intelligence.service;

public interface PromptService {

    String buildPrompt(
            Long userId,
            String userMessage
    );
}
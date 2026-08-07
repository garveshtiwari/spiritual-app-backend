package com.garveshtiwari.spiritual_app_backend.intelligence.prompt.service;

public interface PromptService {

    String buildPrompt(
            Long userId,
            String userMessage
    );
}
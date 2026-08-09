package com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalResponse;

public interface PromptService {

    String buildPrompt(
            Long userId,
            Long conversationId,
            String userMessage,
            RetrievalResponse retrievalResponse
    );
}
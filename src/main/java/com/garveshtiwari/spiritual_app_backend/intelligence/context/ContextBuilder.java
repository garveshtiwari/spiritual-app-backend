package com.garveshtiwari.spiritual_app_backend
        .intelligence.context;

public interface ContextBuilder {

    String buildContext(
            Long userId,
            Long conversationId,
            String userMessage
    );
}
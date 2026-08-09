package com.garveshtiwari.spiritual_app_backend
        .intelligence.chat.service;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.Conversation;
import com.garveshtiwari.spiritual_app_backend
        .user.entity.User;
import reactor.core.publisher.Flux;

public interface ChatAiService {

    String generateResponse(
            User user,
            Conversation conversation,
            String userMessage
    );

    Flux<String> generateResponseStream(
            User user,
            Conversation conversation,
            String userMessage
    );
}
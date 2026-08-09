package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.service;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.Conversation;

public interface ConversationSummarizationService {

    void updateConversationMemory(
            Conversation conversation
    );
}
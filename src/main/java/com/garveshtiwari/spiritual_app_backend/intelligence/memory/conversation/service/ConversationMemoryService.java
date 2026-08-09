package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.service;

import com.garveshtiwari.spiritual_app_backend.chat.entity.ChatMessage;

import java.util.List;

public interface ConversationMemoryService {

    String getSummary(
            Long conversationId
    );

    List<ChatMessage> getRecentMessages(
            Long conversationId
    );

    List<ChatMessage> getUnsummarizedMessages(
            Long conversationId
    );

    void updateSummary(
            Long conversationId,
            String summary,
            ChatMessage lastSummarizedMessage
    );
}
package com.garveshtiwari.spiritual_app_backend.chat.service;

import com.garveshtiwari.spiritual_app_backend.chat.dto.ChatMessageRequest;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ChatMessageResponse;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ConversationRequest;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ConversationResponse;

import java.util.List;

public interface ChatService {

    ConversationResponse createConversation(
            ConversationRequest request
    );

    List<ConversationResponse> getConversations();

    List<ChatMessageResponse> getMessages(
            Long conversationId
    );

    ChatMessageResponse sendMessage(
            Long conversationId,
            ChatMessageRequest request
    );

    void deleteConversation(
            Long conversationId
    );
}
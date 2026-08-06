package com.garveshtiwari.spiritual_app_backend.chat.mapper;

import com.garveshtiwari.spiritual_app_backend.chat.dto.ChatMessageResponse;
import com.garveshtiwari.spiritual_app_backend.chat.entity.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {

    public ChatMessageResponse toResponse(
            ChatMessage chatMessage
    ) {

        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .senderType(chatMessage.getSenderType())
                .message(chatMessage.getMessage())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
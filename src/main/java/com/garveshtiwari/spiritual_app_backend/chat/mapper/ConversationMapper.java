package com.garveshtiwari.spiritual_app_backend.chat.mapper;

import com.garveshtiwari.spiritual_app_backend.chat.dto.ConversationResponse;
import com.garveshtiwari.spiritual_app_backend.chat.entity.Conversation;
import org.springframework.stereotype.Component;

@Component
public class ConversationMapper {

    public ConversationResponse toResponse(
            Conversation conversation
    ) {

        return ConversationResponse.builder()
                .id(conversation.getId())
                .title(conversation.getTitle())
                .createdAt(conversation.getCreatedAt())
                .updatedAt(conversation.getUpdatedAt())
                .build();
    }
}
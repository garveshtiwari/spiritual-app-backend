package com.garveshtiwari.spiritual_app_backend.chat.repository;

import com.garveshtiwari.spiritual_app_backend.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage>
    findByConversationIdOrderByCreatedAtAsc(
            Long conversationId
    );
}
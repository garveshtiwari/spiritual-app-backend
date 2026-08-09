package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.repository;

import com.garveshtiwari.spiritual_app_backend.chat.entity.Conversation;
import com.garveshtiwari.spiritual_app_backend.intelligence.memory.conversation.entity.ConversationMemory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationMemoryRepository
        extends JpaRepository<
        ConversationMemory,
        Long> {

    Optional<ConversationMemory>
    findByConversation(
            Conversation conversation
    );

    Optional<ConversationMemory>
    findByConversationId(
            Long conversationId
    );

    boolean existsByConversation(
            Conversation conversation
    );
}
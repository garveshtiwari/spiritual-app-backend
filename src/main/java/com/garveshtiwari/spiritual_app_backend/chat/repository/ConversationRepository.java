package com.garveshtiwari.spiritual_app_backend.chat.repository;

import com.garveshtiwari.spiritual_app_backend.chat.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository
        extends JpaRepository<Conversation, Long> {

    List<Conversation>
    findByUserIdOrderByUpdatedAtDesc(
            Long userId
    );

    Optional<Conversation> findByIdAndUserId(
            Long id,
            Long userId
    );
}
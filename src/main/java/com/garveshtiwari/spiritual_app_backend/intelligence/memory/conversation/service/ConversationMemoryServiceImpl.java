package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.service;

import com.garveshtiwari.spiritual_app_backend.chat.entity.ChatMessage;
import com.garveshtiwari.spiritual_app_backend.chat.entity.Conversation;
import com.garveshtiwari.spiritual_app_backend.chat.repository.ChatMessageRepository;
import com.garveshtiwari.spiritual_app_backend.chat.repository.ConversationRepository;
import com.garveshtiwari.spiritual_app_backend.intelligence.config.MemoryProperties;
import com.garveshtiwari.spiritual_app_backend.intelligence.memory.conversation.entity.ConversationMemory;
import com.garveshtiwari.spiritual_app_backend.intelligence.memory.conversation.repository.ConversationMemoryRepository;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationMemoryServiceImpl
        implements ConversationMemoryService {

    private final ConversationMemoryRepository
            repository;

    private final ConversationRepository
            conversationRepository;

    private final ChatMessageRepository
            chatMessageRepository;

    private final MemoryProperties
            memoryProperties;

    private ConversationMemory getOrCreate(
            Conversation conversation
    ) {

        return repository
                .findByConversation(conversation)
                .orElseGet(() -> {

                    ConversationMemory memory =
                            ConversationMemory.builder()
                                    .conversation(conversation)
                                    .summary("")
                                    .summaryVersion(
                                            memoryProperties.getSummaryVersion()
                                    )
                                    .createdAt(
                                            LocalDateTime.now()
                                    )
                                    .updatedAt(
                                            LocalDateTime.now()
                                    )
                                    .build();

                    return repository.save(
                            memory
                    );
                });
    }

    private Conversation getConversation(
            Long conversationId
    ) {

        return conversationRepository
                .findById(conversationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Conversation not found."
                        )
                );
    }

    @Override
    public String getSummary(
            Long conversationId
    ) {

        Conversation conversation =
                getConversation(
                        conversationId
                );

        return getOrCreate(
                conversation
        ).getSummary();
    }

    @Override
    public void updateSummary(
            Long conversationId,
            String summary,
            ChatMessage lastSummarizedMessage
    ) {

        Conversation conversation =
                getConversation(
                        conversationId
                );

        ConversationMemory memory =
                getOrCreate(
                        conversation
                );

        memory.setSummary(
                summary
        );

        memory.setLastSummarizedMessage(
                lastSummarizedMessage
        );

        memory.setSummaryVersion(
                memoryProperties
                        .getSummaryVersion()
        );

        memory.setUpdatedAt(
                LocalDateTime.now()
        );

        repository.save(
                memory
        );
    }

    @Override
    public List<ChatMessage> getRecentMessages(
            Long conversationId
    ) {

        return chatMessageRepository
                .findByConversationIdOrderByCreatedAtDesc(
                        conversationId,
                        PageRequest.of(
                                0,
                                memoryProperties
                                        .getRecentMessages()
                        )
                );
    }

    @Override
    public List<ChatMessage> getUnsummarizedMessages(
            Long conversationId
    ) {

        Conversation conversation =
                getConversation(
                        conversationId
                );

        ConversationMemory memory =
                getOrCreate(
                        conversation
                );

        if (memory.getLastSummarizedMessage() == null) {

            return chatMessageRepository
                    .findByConversationIdOrderByCreatedAtAsc(
                            conversationId
                    );
        }

        return chatMessageRepository
                .findByConversationIdAndIdGreaterThanOrderByCreatedAtAsc(
                        conversationId,
                        memory.getLastSummarizedMessage()
                                .getId()
                );
    }
}
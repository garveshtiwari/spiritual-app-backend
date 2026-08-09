package com.garveshtiwari.spiritual_app_backend.chat.service;

import com.garveshtiwari.spiritual_app_backend.chat.dto.ChatMessageRequest;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ChatMessageResponse;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ConversationRequest;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ConversationResponse;
import com.garveshtiwari.spiritual_app_backend.chat.entity.ChatMessage;
import com.garveshtiwari.spiritual_app_backend.chat.entity.Conversation;
import com.garveshtiwari.spiritual_app_backend.chat.mapper.ChatMessageMapper;
import com.garveshtiwari.spiritual_app_backend.chat.mapper.ConversationMapper;
import com.garveshtiwari.spiritual_app_backend.chat.repository.ChatMessageRepository;
import com.garveshtiwari.spiritual_app_backend.chat.repository.ConversationRepository;
import com.garveshtiwari.spiritual_app_backend.common.enums.SenderType;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend.intelligence.chat.service.ChatAiService;
import com.garveshtiwari.spiritual_app_backend.intelligence.memory.pipeline.KnowledgePipelineService;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository
            conversationRepository;

    private final ChatMessageRepository
            chatMessageRepository;

    private final ConversationMapper
            conversationMapper;

    private final ChatMessageMapper
            chatMessageMapper;

    private final UserRepository
            userRepository;

    private final ChatAiService
            chatAiService;

    private final KnowledgePipelineService
            knowledgePipelineService;


    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found."
                        )
                );
    }


    private Conversation getConversationByUser(
            Long conversationId
    ) {

        User user =
                getCurrentUser();

        return conversationRepository
                .findByIdAndUserId(
                        conversationId,
                        user.getId()
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Conversation not found."
                        )
                );
    }


    @Override
    public ConversationResponse createConversation(
            ConversationRequest request
    ) {

        User user =
                getCurrentUser();

        LocalDateTime now =
                LocalDateTime.now();

        Conversation conversation =
                Conversation
                        .builder()
                        .user(user)
                        .title(request.getTitle())
                        .createdAt(now)
                        .updatedAt(now)
                        .build();

        conversationRepository.save(
                conversation
        );

        return conversationMapper.toResponse(
                conversation
        );
    }


    @Override
    public List<ConversationResponse> getConversations() {

        User user =
                getCurrentUser();

        return conversationRepository
                .findByUserIdOrderByUpdatedAtDesc(
                        user.getId()
                )
                .stream()
                .map(conversationMapper::toResponse)
                .toList();
    }


    @Override
    public List<ChatMessageResponse> getMessages(
            Long conversationId
    ) {

        Conversation conversation =
                getConversationByUser(
                        conversationId
                );

        return chatMessageRepository
                .findByConversationIdOrderByCreatedAtAsc(
                        conversation.getId()
                )
                .stream()
                .map(chatMessageMapper::toResponse)
                .toList();
    }


    @Override
    public ChatMessageResponse sendMessage(
            Long conversationId,
            ChatMessageRequest request
    ) {

        long totalStart =
                System.currentTimeMillis();

        User user =
                getCurrentUser();

        Conversation conversation =
                getConversationByUser(
                        conversationId
                );


        /*
         * ---------------------------------------------------------
         * SAVE USER MESSAGE
         * ---------------------------------------------------------
         */

        long saveUserStart =
                System.currentTimeMillis();

        ChatMessage userMessage =
                ChatMessage
                        .builder()
                        .conversation(conversation)
                        .senderType(
                                SenderType.USER
                        )
                        .message(
                                request.getMessage()
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        chatMessageRepository.save(
                userMessage
        );

        log.info(
                "SAVE USER MESSAGE TIME: {} ms",
                System.currentTimeMillis()
                        - saveUserStart
        );


        /*
         * ---------------------------------------------------------
         * AI RESPONSE
         * ---------------------------------------------------------
         */

        long aiStart =
                System.currentTimeMillis();

        String aiResponse =
                chatAiService.generateResponse(
                        user,
                        conversation,
                        request.getMessage()
                );

        long aiTime =
                System.currentTimeMillis()
                        - aiStart;

        log.info(
                "CHAT AI SERVICE TIME: {} ms",
                aiTime
        );


        /*
         * ---------------------------------------------------------
         * SAVE ASSISTANT MESSAGE
         * ---------------------------------------------------------
         */

        long saveAssistantStart =
                System.currentTimeMillis();

        ChatMessage assistantMessage =
                ChatMessage
                        .builder()
                        .conversation(conversation)
                        .senderType(
                                SenderType.ASSISTANT
                        )
                        .message(
                                aiResponse
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        chatMessageRepository.save(
                assistantMessage
        );

        log.info(
                "SAVE ASSISTANT MESSAGE TIME: {} ms",
                System.currentTimeMillis()
                        - saveAssistantStart
        );


        /*
         * ---------------------------------------------------------
         * UPDATE CONVERSATION
         * ---------------------------------------------------------
         */

        conversation.setUpdatedAt(
                LocalDateTime.now()
        );

        conversationRepository.save(
                conversation
        );


        /*
         * ---------------------------------------------------------
         * KNOWLEDGE PIPELINE
         *
         * Currently synchronous.
         * We will make this asynchronous later because
         * memory extraction should not delay the chat response.
         * ---------------------------------------------------------
         */

        long pipelineStart =
                System.currentTimeMillis();

        try {

            knowledgePipelineService
                    .processInteraction(
                            conversation.getId()
                    );

        } catch (Exception exception) {

            log.error(
                    "Knowledge pipeline failed for conversation {}",
                    conversation.getId(),
                    exception
            );
        }

        long pipelineTime =
                System.currentTimeMillis()
                        - pipelineStart;

        log.info(
                "KNOWLEDGE PIPELINE TIME: {} ms",
                pipelineTime
        );


        /*
         * ---------------------------------------------------------
         * TOTAL REQUEST TIME
         * ---------------------------------------------------------
         */

        long totalTime =
                System.currentTimeMillis()
                        - totalStart;

        log.info(
                "TOTAL SEND MESSAGE TIME: {} ms",
                totalTime
        );


        return chatMessageMapper.toResponse(
                assistantMessage
        );
    }


    @Override
    public void deleteConversation(
            Long conversationId
    ) {

        Conversation conversation =
                getConversationByUser(
                        conversationId
                );

        conversationRepository.delete(
                conversation
        );
    }
}
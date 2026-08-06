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
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final ConversationMapper conversationMapper;

    private final ChatMessageMapper chatMessageMapper;

    private final UserRepository userRepository;

    private User getCurrentUser() {

        String email = SecurityContextHolder
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

        User user = getCurrentUser();

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

        User user = getCurrentUser();

        Conversation conversation = Conversation
                .builder()
                .user(user)
                .title(request.getTitle())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        conversationRepository.save(conversation);

        return conversationMapper.toResponse(
                conversation
        );
    }

    @Override
    public List<ConversationResponse> getConversations() {

        User user = getCurrentUser();

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

        Conversation conversation =
                getConversationByUser(
                        conversationId
                );

        ChatMessage chatMessage = ChatMessage
                .builder()
                .conversation(conversation)
                .senderType(SenderType.USER)
                .message(request.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        chatMessageRepository.save(chatMessage);

        conversation.setUpdatedAt(
                LocalDateTime.now()
        );

        conversationRepository.save(
                conversation
        );

        return chatMessageMapper.toResponse(
                chatMessage
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
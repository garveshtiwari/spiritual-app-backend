package com.garveshtiwari.spiritual_app_backend
        .intelligence.conversation.provider;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.ChatMessage;
import com.garveshtiwari.spiritual_app_backend
        .chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConversationProviderImpl
        implements ConversationProvider {

    private final ChatMessageRepository
            chatMessageRepository;

    @Override
    public String getConversation(
            Long conversationId
    ) {

        List<ChatMessage> messages =
                chatMessageRepository
                        .findByConversationIdOrderByCreatedAtAsc(
                                conversationId
                        );

        if (messages.isEmpty()) {
            return "";
        }

        List<ChatMessage> userMessages =
                messages
                        .stream()
                        .filter(message ->
                                "USER".equals(
                                        message
                                                .getSenderType()
                                                .name()
                                )
                        )
                        .toList();

        if (userMessages.isEmpty()) {
            return "";
        }

        ChatMessage latestUserMessage =
                userMessages.get(
                        userMessages.size() - 1
                );

        StringBuilder conversation =
                new StringBuilder();

        conversation.append("""
                IMPORTANT MEMORY EXTRACTION RULE:

                The latest user message represents the user's
                most recent statement.

                When a newer user statement contradicts or updates
                an older statement about the same mutable fact,
                prefer the newer statement.

                Older statements may represent historical information
                and must not automatically override the latest
                statement.

                ==============================
                LATEST USER MESSAGE
                ==============================

                """);

        conversation.append(
                latestUserMessage.getMessage()
        );

        conversation.append("\n\n");

        conversation.append("""
                ==============================
                PREVIOUS USER MESSAGES
                ==============================

                """);

        for (int i = 0;
             i < userMessages.size() - 1;
             i++) {

            ChatMessage message =
                    userMessages.get(i);

            conversation.append("- ")
                    .append(
                            message.getMessage()
                    )
                    .append("\n");
        }

        return conversation.toString();
    }
}
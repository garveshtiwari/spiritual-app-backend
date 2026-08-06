package com.garveshtiwari.spiritual_app_backend
        .intelligence.context.chat;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.ChatMessage;
import com.garveshtiwari.spiritual_app_backend
        .chat.entity.Conversation;
import com.garveshtiwari.spiritual_app_backend
        .chat.repository.ChatMessageRepository;
import com.garveshtiwari.spiritual_app_backend
        .chat.repository.ConversationRepository;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatContextBuilder
        implements ContextBuilder {

    private static final int CONVERSATION_LIMIT = 1;

    private final ConversationRepository
            conversationRepository;

    private final ChatMessageRepository
            chatMessageRepository;

    @Override
    public String buildContext(
            Long userId
    ) {

        List<Conversation> conversations =
                conversationRepository
                        .findByUserIdOrderByUpdatedAtDesc(
                                userId
                        );

        if (conversations.isEmpty()) {
            return "";
        }

        StringBuilder context =
                new StringBuilder();

        context.append("""
                Previous conversation:

                """);

        int count = Math.min(
                CONVERSATION_LIMIT,
                conversations.size()
        );

        for (int i = 0; i < count; i++) {

            Conversation conversation =
                    conversations.get(i);

            List<ChatMessage> messages =
                    chatMessageRepository
                            .findByConversationIdOrderByCreatedAtAsc(
                                    conversation.getId()
                            );

            for (ChatMessage message : messages) {

                context.append(
                        message.getSenderType()
                );

                context.append(": ");

                context.append(
                        message.getMessage()
                );

                context.append("\n");
            }
        }

        return context.toString();
    }
}
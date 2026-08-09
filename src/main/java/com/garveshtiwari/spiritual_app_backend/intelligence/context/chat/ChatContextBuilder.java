package com.garveshtiwari.spiritual_app_backend
        .intelligence.context.chat;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.ChatMessage;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.service.ConversationMemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatContextBuilder
        implements ContextBuilder {

    private final ConversationMemoryService
            conversationMemoryService;

    @Override
    public String buildContext(
            Long userId,
            Long conversationId,
            String userMessage
    ) {

        if (conversationId == null) {
            return "";
        }

        String summary =
                conversationMemoryService
                        .getSummary(
                                conversationId
                        );

        List<ChatMessage> recentMessages =
                conversationMemoryService
                        .getRecentMessages(
                                conversationId
                        );

        Collections.reverse(
                recentMessages
        );

        StringBuilder context =
                new StringBuilder();

        if (!summary.isBlank()) {

            context.append("""
                    Conversation Summary:

                    """);

            context.append(summary)
                    .append("\n\n");
        }

        if (!recentMessages.isEmpty()) {

            context.append("""
                    Recent Messages:

                    """);

            for (ChatMessage message :
                    recentMessages) {

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
package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.builder;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConversationSummaryBuilder {

    public String build(
            List<ChatMessage> messages
    ) {

        StringBuilder builder =
                new StringBuilder();

        for (ChatMessage message : messages) {

            builder.append(
                    message.getSenderType()
            );

            builder.append(":\n");

            builder.append(
                    message.getMessage()
            );

            builder.append("\n\n");
        }

        return builder.toString();
    }
}
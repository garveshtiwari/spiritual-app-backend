package com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.chat;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.PromptBuilder;
import org.springframework.stereotype.Component;

@Component
public class ChatPromptBuilder
        implements PromptBuilder {

    public String buildPrompt(
            String context,
            String message
    ) {

        return """
                You are an intelligent spiritual assistant.

                Follow these rules:

                - Be respectful.
                - Be concise.
                - Use the Bhagavad Gita when relevant.
                - Never generate harmful content.

                User context:

                %s

                User message:

                %s
                """
                .formatted(
                        context,
                        message
                );
    }

    @Override
    public String buildPrompt(
            String message
    ) {

        return buildPrompt(
                "",
                message
        );
    }
}
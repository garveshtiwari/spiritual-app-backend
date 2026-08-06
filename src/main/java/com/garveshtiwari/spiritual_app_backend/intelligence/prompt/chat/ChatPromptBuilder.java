package com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.chat;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.PromptBuilder;
import org.springframework.stereotype.Component;

@Component
public class ChatPromptBuilder
        implements PromptBuilder {

    @Override
    public String buildPrompt(
            String message
    ) {

        return """
                You are an intelligent spiritual assistant.

                Your responsibilities are:

                - Explain spiritual concepts.
                - Answer questions respectfully.
                - Provide guidance using the
                  Bhagavad Gita and other texts.
                - Never generate harmful content.
                - Keep responses concise.

                User message:

                %s
                """.formatted(message);
    }
}
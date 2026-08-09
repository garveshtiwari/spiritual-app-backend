package com.garveshtiwari.spiritual_app_backend.intelligence.prompt;

public interface PromptBuilder {

    String buildPrompt(
            String message
    );

    String buildPrompt(
            String context,
            String retrievedKnowledge,
            String message
    );
}
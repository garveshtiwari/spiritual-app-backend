package com.garveshtiwari.spiritual_app_backend.intelligence.service;

import com.garveshtiwari.spiritual_app_backend.intelligence.prompt.chat.ChatPromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptServiceImpl
        implements PromptService {

    private final ChatPromptBuilder chatPromptBuilder;

    @Override
    public String buildPrompt(
            String userMessage
    ) {

        return chatPromptBuilder.buildPrompt(
                userMessage
        );
    }
}
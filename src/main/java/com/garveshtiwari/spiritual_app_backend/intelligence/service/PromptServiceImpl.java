package com.garveshtiwari.spiritual_app_backend
        .intelligence.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.chat.ChatPromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptServiceImpl
        implements PromptService {

    private final ChatPromptBuilder
            chatPromptBuilder;

    private final ContextService
            contextService;

    @Override
    public String buildPrompt(
            Long userId,
            String userMessage
    ) {

        String context =
                contextService.buildContext(
                        userId
                );

        return chatPromptBuilder.buildPrompt(
                context,
                userMessage
        );
    }
}
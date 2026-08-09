package com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.service.ContextService;
import com.garveshtiwari.spiritual_app_backend.intelligence.prompt.builder.RetrievedKnowledgeBuilder;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.chat.ChatPromptBuilder;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalResponse;
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

    private final RetrievedKnowledgeBuilder
            retrievedKnowledgeBuilder;

    @Override
    public String buildPrompt(
            Long userId,
            Long conversationId,
            String userMessage,
            RetrievalResponse retrievalResponse
    ) {

        String context =
                contextService.buildContext(
                        userId,
                        conversationId,
                        userMessage
                );

        String retrievedKnowledge =
                retrievedKnowledgeBuilder.build(
                        retrievalResponse
                );

        return chatPromptBuilder.buildPrompt(
                context,
                retrievedKnowledge,
                userMessage
        );
    }
}
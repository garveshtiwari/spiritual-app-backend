package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.service;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.ChatMessage;
import com.garveshtiwari.spiritual_app_backend
        .chat.entity.Conversation;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.config.MemoryProperties;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.service.AiService;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.builder.ConversationSummaryBuilder;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.dto.ConversationSummaryRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.system.ConversationSummaryPromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationSummarizationServiceImpl
        implements ConversationSummarizationService {

    private final ConversationMemoryService
            conversationMemoryService;

    private final ConversationSummaryBuilder
            conversationSummaryBuilder;

    private final ConversationSummaryPromptBuilder
            conversationSummaryPromptBuilder;

    private final AiService
            aiService;

    private final MemoryProperties
            memoryProperties;

    @Override
    public void updateConversationMemory(
            Conversation conversation
    ) {

        List<ChatMessage> unsummarizedMessages =
                conversationMemoryService
                        .getUnsummarizedMessages(
                                conversation.getId()
                        );

        if (unsummarizedMessages.size()
                < memoryProperties.getSummaryThreshold()) {

            return;
        }

        String conversationText =
                conversationSummaryBuilder.build(
                        unsummarizedMessages
                );

        ConversationSummaryRequest request =
                ConversationSummaryRequest
                        .builder()
                        .existingSummary(
                                conversationMemoryService
                                        .getSummary(
                                                conversation.getId()
                                        )
                        )
                        .conversation(
                                conversationText
                        )
                        .build();

        String prompt =
                conversationSummaryPromptBuilder
                        .buildPrompt(
                                request
                        );

        String updatedSummary =
                aiService.generateResponse(
                        prompt
                );

        conversationMemoryService
                .updateSummary(
                        conversation.getId(),
                        updatedSummary,
                        unsummarizedMessages.getLast()
                );
    }
}
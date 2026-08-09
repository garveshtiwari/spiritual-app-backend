package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemoryExtractionRequest {

    /**
     * Existing long-term memories of the user.
     */
    private String existingMemories;

    /**
     * Latest conversation summary.
     */
    private String conversationSummary;

    /**
     * Latest conversation messages.
     */
    private String recentConversation;
}
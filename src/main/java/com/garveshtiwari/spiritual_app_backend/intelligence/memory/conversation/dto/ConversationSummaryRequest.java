package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversationSummaryRequest {

    /**
     * Existing conversation summary.
     */
    private String existingSummary;

    /**
     * Newly added conversation messages
     * that haven't been summarized yet.
     */
    private String conversation;
}
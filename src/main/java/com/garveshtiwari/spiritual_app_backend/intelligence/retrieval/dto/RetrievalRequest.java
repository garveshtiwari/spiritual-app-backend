package com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalRequest {

    /**
     * User asking the question.
     * Will be used later for personalization.
     */
    private Long userId;

    /**
     * User question.
     */
    private String question;

    /**
     * Maximum number of documents to retrieve.
     */
    @Builder.Default
    private Integer limit = 5;
}
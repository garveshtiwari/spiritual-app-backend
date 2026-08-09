package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchResult {

    private Long documentId;

    private String title;

    private String content;

    private String metadata;

    /**
     * Raw vector similarity/distance from pgvector.
     */
    private Double similarity;

    /**
     * Personalized score after ranking strategies.
     */
    @Builder.Default
    private Double rankingScore = 0.0;
}
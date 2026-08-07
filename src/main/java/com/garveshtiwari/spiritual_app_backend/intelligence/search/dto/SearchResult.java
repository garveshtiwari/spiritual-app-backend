package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchResult {

    private Long documentId;

    private String title;

    private String content;

    private String metadata;

    private Double similarity;
}
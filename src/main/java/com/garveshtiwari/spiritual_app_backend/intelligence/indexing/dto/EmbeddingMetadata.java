package com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmbeddingMetadata {

    private String book;

    private Integer chapter;

    private Integer verse;

    private String language;

    private String category;
}
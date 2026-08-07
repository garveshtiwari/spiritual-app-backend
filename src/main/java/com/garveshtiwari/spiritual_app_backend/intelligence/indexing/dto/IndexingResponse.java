package com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class IndexingResponse {

    private String message;

    private Long verseId;

    private Integer indexedCount;

    private Integer skippedCount;
}
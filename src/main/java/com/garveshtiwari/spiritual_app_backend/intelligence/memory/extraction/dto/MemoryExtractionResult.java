package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryAction;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemoryExtractionResult {

    private String memory;

    private MemoryCategory category;

    private Double confidence;

    /**
     * What should happen with this extracted memory.
     */
    private MemoryAction action;

    /**
     * Existing memory ID that should be updated.
     *
     * Null when action is CREATE or IGNORE.
     */
    private Long existingMemoryId;
}
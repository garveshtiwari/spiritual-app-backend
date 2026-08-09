package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.dto;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryCategory;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryImportance;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemorySource;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LongTermMemoryResponse {

    private Long id;

    private String content;

    private MemoryCategory category;

    private MemoryImportance importance;

    private MemoryStatus status;

    private MemorySource source;

    private Integer confidence;

    private Boolean pinned;

    private Integer progress;

    private LocalDateTime validUntil;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastAccessedAt;

    private Integer accessCount;
}
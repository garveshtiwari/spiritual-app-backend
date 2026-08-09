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
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LongTermMemoryRequest {

    private String content;

    private String embedding;

    private MemoryCategory category;

    private MemoryImportance importance;

    private MemoryStatus status;

    private MemorySource source;

    private Integer confidence;

    private Boolean pinned;

    private Integer progress;

    private LocalDateTime validUntil;
}
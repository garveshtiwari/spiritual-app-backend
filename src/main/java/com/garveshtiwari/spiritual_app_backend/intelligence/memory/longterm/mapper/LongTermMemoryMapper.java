package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.mapper;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.dto.LongTermMemoryResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.entity.LongTermMemory;
import org.springframework.stereotype.Component;

@Component
public class LongTermMemoryMapper {

    public LongTermMemoryResponse toResponse(
            LongTermMemory memory
    ) {

        if (memory == null) {
            return null;
        }

        return LongTermMemoryResponse
                .builder()
                .id(memory.getId())
                .content(memory.getContent())
                .category(memory.getCategory())
                .importance(memory.getImportance())
                .status(memory.getStatus())
                .source(memory.getSource())
                .confidence(memory.getConfidence())
                .pinned(memory.getPinned())
                .progress(memory.getProgress())
                .validUntil(memory.getValidUntil())
                .createdAt(memory.getCreatedAt())
                .updatedAt(memory.getUpdatedAt())
                .lastAccessedAt(memory.getLastAccessedAt())
                .accessCount(memory.getAccessCount())
                .build();
    }
}
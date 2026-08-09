package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.mapper;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryImportance;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemorySource;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryStatus;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto.MemoryExtractionResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.entity.LongTermMemory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MemoryExtractionMapper {

    public LongTermMemory toEntity(
            MemoryExtractionResult result
    ) {

        if (result == null) {
            return null;
        }

        Integer confidence =
                result.getConfidence() == null
                        ? 0
                        : (int) Math.round(
                        result.getConfidence() * 100
                );

        return LongTermMemory
                .builder()
                .content(
                        result.getMemory()
                )
                .category(
                        result.getCategory()
                )
                .importance(
                        determineImportance(
                                confidence
                        )
                )
                .status(
                        MemoryStatus.ACTIVE
                )
                .source(
                        MemorySource.CHAT
                )
                .confidence(
                        confidence
                )
                .pinned(
                        false
                )
                .validUntil(
                        null
                )
                .createdAt(
                        LocalDateTime.now()
                )
                .updatedAt(
                        LocalDateTime.now()
                )
                .lastAccessedAt(
                        null
                )
                .accessCount(
                        0
                )
                .progress(
                        0
                )
                .build();
    }

    private MemoryImportance determineImportance(
            Integer confidence
    ) {

        if (confidence >= 90) {
            return MemoryImportance.HIGH;
        }

        if (confidence >= 70) {
            return MemoryImportance.MEDIUM;
        }

        return MemoryImportance.LOW;
    }
}
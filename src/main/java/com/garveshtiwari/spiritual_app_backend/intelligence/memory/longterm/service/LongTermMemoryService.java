package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.dto.LongTermMemoryResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.entity.LongTermMemory;

import java.util.List;

public interface LongTermMemoryService {

    LongTermMemory store(
            LongTermMemory memory
    );

    List<LongTermMemoryResponse> findAll();

    List<LongTermMemoryResponse> findActive();

    LongTermMemory findById(
            Long id
    );

    void markAccessed(
            LongTermMemory memory
    );

    String getExistingMemories();

    /**
     * Finds long-term memories that are relevant
     * to the supplied user message.
     *
     * This is the first deterministic retrieval layer.
     * Semantic embeddings will be added later.
     */
    List<LongTermMemory> findRelevantMemories(
            String query,
            int limit
    );
}
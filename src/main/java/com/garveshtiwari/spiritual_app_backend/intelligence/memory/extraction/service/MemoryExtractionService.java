package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto.MemoryExtractionRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto.MemoryExtractionResult;

import java.util.List;

public interface MemoryExtractionService {

    List<MemoryExtractionResult> extract(
            MemoryExtractionRequest request
    );
}
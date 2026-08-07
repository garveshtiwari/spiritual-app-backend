package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.dto.EmbeddingResult;

public interface EmbeddingService {

    EmbeddingResult generateEmbedding(
            String text
    );
}
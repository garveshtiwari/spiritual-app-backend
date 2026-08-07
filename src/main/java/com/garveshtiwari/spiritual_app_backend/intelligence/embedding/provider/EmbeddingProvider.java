package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.provider;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.dto.EmbeddingResult;

public interface EmbeddingProvider {

    EmbeddingResult generateEmbedding(
            String text
    );
}
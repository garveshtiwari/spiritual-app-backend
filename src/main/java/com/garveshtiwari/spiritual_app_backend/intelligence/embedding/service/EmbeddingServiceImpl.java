package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.dto.EmbeddingResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.provider.EmbeddingProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl
        implements EmbeddingService {

    private final EmbeddingProviderFactory
            providerFactory;

    @Override
    public EmbeddingResult generateEmbedding(
            String text
    ) {

        return providerFactory
                .getProvider()
                .generateEmbedding(text);
    }
}
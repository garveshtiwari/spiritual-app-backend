package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmbeddingProviderFactory {

    private final OpenAiEmbeddingProvider
            openAiEmbeddingProvider;

    public EmbeddingProvider getProvider() {

        return openAiEmbeddingProvider;
    }
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.provider.openai;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenAiEmbeddingRequest {

    private String model;

    private String input;
}
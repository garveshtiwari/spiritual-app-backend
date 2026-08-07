package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmbeddingResult {

    private float[] vector;

    private Integer dimensions;

    private String model;
}
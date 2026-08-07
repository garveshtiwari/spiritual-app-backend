package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.provider.openai;

import lombok.Getter;

import java.util.List;

@Getter
public class OpenAiEmbeddingResponse {

    private List<Data> data;

    @Getter
    public static class Data {

        private List<Float> embedding;

        private Integer index;
    }
}
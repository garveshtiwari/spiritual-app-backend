package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.provider;

import com.garveshtiwari.spiritual_app_backend
        .common.exception.AiException;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.config.OpenAiProperties;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.dto.EmbeddingResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.provider.openai.OpenAiEmbeddingRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.provider.openai.OpenAiEmbeddingResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class OpenAiEmbeddingProvider
        implements EmbeddingProvider {

    private final OpenAiProperties properties;

    private final WebClient webClient;

    public OpenAiEmbeddingProvider(
            WebClient.Builder builder,
            OpenAiProperties properties
    ) {

        this.properties = properties;

        this.webClient = builder
                .baseUrl(
                        properties.getBaseUrl()
                )
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + properties.getApiKey()
                )
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    @Override
    public EmbeddingResult generateEmbedding(
            String text
    ) {

        OpenAiEmbeddingRequest request =
                OpenAiEmbeddingRequest
                        .builder()
                        .model(
                                properties.getEmbeddingModel()
                        )
                        .input(text)
                        .build();

        OpenAiEmbeddingResponse response =
                webClient
                        .post()
                        .uri("/v1/embeddings")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(
                                OpenAiEmbeddingResponse.class
                        )
                        .block();

        if (response == null
                || response.getData() == null
                || response.getData().isEmpty()) {

            throw new AiException(
                    "Unable to generate embedding."
            );
        }

        List<Float> embedding =
                response.getData()
                        .get(0)
                        .getEmbedding();

        float[] vector =
                new float[embedding.size()];

        for (int i = 0; i < embedding.size(); i++) {

            vector[i] = embedding.get(i);
        }

        return EmbeddingResult
                .builder()
                .vector(vector)
                .dimensions(vector.length)
                .model(
                        properties.getEmbeddingModel()
                )
                .build();
    }
}
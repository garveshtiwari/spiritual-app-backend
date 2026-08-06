package com.garveshtiwari.spiritual_app_backend
        .intelligence.provider.openai;

import com.garveshtiwari.spiritual_app_backend
        .common.exception.AiException;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.config.OpenAiProperties;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.provider.AiProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class OpenAiProvider implements AiProvider {

    private final OpenAiProperties properties;

    private final WebClient webClient;

    public OpenAiProvider(
            WebClient.Builder builder,
            OpenAiProperties properties
    ) {

        this.properties = properties;

        this.webClient = builder
                .baseUrl(properties.getBaseUrl())
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
    public String generateResponse(
            String prompt
    ) {

        OpenAiRequest request =
                OpenAiRequest.builder()
                        .model(properties.getModel())
                        .messages(
                                List.of(
                                        OpenAiRequest.Message
                                                .builder()
                                                .role("user")
                                                .content(prompt)
                                                .build()
                                )
                        )
                        .build();

        OpenAiResponse response =
                webClient
                        .post()
                        .uri("/v1/chat/completions")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(OpenAiResponse.class)
                        .block();

        if (response == null
                || response.getChoices() == null
                || response.getChoices().isEmpty()) {

            throw new AiException(
                    "Unable to generate response."
            );
        }

        return response
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }
}
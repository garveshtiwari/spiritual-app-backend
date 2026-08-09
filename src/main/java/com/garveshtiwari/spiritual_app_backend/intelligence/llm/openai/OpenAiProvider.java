package com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garveshtiwari.spiritual_app_backend
        .common.exception.AiException;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.config.OpenAiProperties;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.AiProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@Component
public class OpenAiProvider
        implements AiProvider {

    private static final int
            MAX_COMPLETION_TOKENS = 500;

    private final OpenAiProperties
            properties;

    private final WebClient
            webClient;

    private final ObjectMapper
            objectMapper;


    public OpenAiProvider(
            WebClient.Builder builder,
            OpenAiProperties properties,
            ObjectMapper objectMapper
    ) {

        this.properties =
                properties;

        this.objectMapper =
                objectMapper;

        this.webClient =
                builder
                        .baseUrl(
                                properties.getBaseUrl()
                        )
                        .defaultHeader(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer "
                                        + properties.getApiKey()
                        )
                        .defaultHeader(
                                HttpHeaders.CONTENT_TYPE,
                                MediaType.APPLICATION_JSON_VALUE
                        )
                        .build();
    }


    /*
     * =========================================================
     * NORMAL NON-STREAMING RESPONSE
     * =========================================================
     */

    @Override
    public String generateResponse(
            String prompt
    ) {

        OpenAiRequest request =
                OpenAiRequest
                        .builder()
                        .model(
                                properties.getModel()
                        )
                        .messages(
                                List.of(
                                        OpenAiRequest.Message
                                                .builder()
                                                .role("user")
                                                .content(prompt)
                                                .build()
                                )
                        )
                        .maxCompletionTokens(
                                MAX_COMPLETION_TOKENS
                        )
                        .stream(false)
                        .build();

        log.info(
                "Sending normal OpenAI request. Model: {}, Prompt characters: {}",
                properties.getModel(),
                prompt == null
                        ? 0
                        : prompt.length()
        );

        long start =
                System.currentTimeMillis();

        OpenAiResponse response =
                webClient
                        .post()
                        .uri(
                                "/v1/chat/completions"
                        )
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(
                                OpenAiResponse.class
                        )
                        .block();

        long duration =
                System.currentTimeMillis()
                        - start;

        log.info(
                "OpenAI normal request completed in {} ms",
                duration
        );

        if (response == null
                || response.getChoices() == null
                || response.getChoices().isEmpty()) {

            throw new AiException(
                    "Unable to generate response."
            );
        }

        if (response.getUsage() != null) {

            log.info(
                    "OpenAI usage - Prompt tokens: {}, Completion tokens: {}, Total tokens: {}",
                    response.getUsage().getPromptTokens(),
                    response.getUsage().getCompletionTokens(),
                    response.getUsage().getTotalTokens()
            );
        }

        return response
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }


    /*
     * =========================================================
     * STREAMING RESPONSE
     * =========================================================
     */

    @Override
    public Flux<String> generateResponseStream(
            String prompt
    ) {

        OpenAiRequest request =
                OpenAiRequest
                        .builder()
                        .model(
                                properties.getModel()
                        )
                        .messages(
                                List.of(
                                        OpenAiRequest.Message
                                                .builder()
                                                .role("user")
                                                .content(prompt)
                                                .build()
                                )
                        )
                        .maxCompletionTokens(
                                MAX_COMPLETION_TOKENS
                        )
                        .stream(true)
                        .build();

        log.info(
                "Starting OpenAI streaming request. " +
                        "Model: {}, Prompt characters: {}, Max output tokens: {}",
                properties.getModel(),
                prompt == null
                        ? 0
                        : prompt.length(),
                MAX_COMPLETION_TOKENS
        );

        long start =
                System.currentTimeMillis();

        return webClient
                .post()
                .uri(
                        "/v1/chat/completions"
                )
                .accept(
                        MediaType.TEXT_EVENT_STREAM
                )
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(String.class)
                .flatMapIterable(
                        this::parseSseChunk
                )
                .doOnNext(
                        chunk -> log.debug(
                                "OpenAI stream chunk received: {}",
                                chunk
                        )
                )
                .doOnComplete(
                        () -> log.info(
                                "OpenAI streaming request completed in {} ms",
                                System.currentTimeMillis()
                                        - start
                        )
                )
                .doOnError(
                        exception -> log.error(
                                "OpenAI streaming request failed after {} ms",
                                System.currentTimeMillis()
                                        - start,
                                exception
                        )
                );
    }


    /*
     * =========================================================
     * SSE PARSER
     * =========================================================
     */

    private List<String> parseSseChunk(
            String rawChunk
    ) {

        if (rawChunk == null ||
                rawChunk.isBlank()) {

            return List.of();
        }

        String[] events =
                rawChunk.split(
                        "\\n\\n"
                );

        List<String> contents =
                new java.util.ArrayList<>();

        for (String event : events) {

            String data =
                    extractData(
                            event
                    );

            if (data == null ||
                    data.isBlank()) {

                continue;
            }

            if ("[DONE]".equals(data)) {

                continue;
            }

            try {

                JsonNode root =
                        objectMapper.readTree(
                                data
                        );

                JsonNode choices =
                        root.get("choices");

                if (choices == null ||
                        !choices.isArray() ||
                        choices.isEmpty()) {

                    continue;
                }

                JsonNode firstChoice =
                        choices.get(0);

                JsonNode delta =
                        firstChoice.get(
                                "delta"
                        );

                if (delta == null) {
                    continue;
                }

                JsonNode content =
                        delta.get(
                                "content"
                        );

                if (content != null &&
                        !content.isNull()) {

                    contents.add(
                            content.asText()
                    );
                }

            } catch (Exception exception) {

                log.warn(
                        "Unable to parse OpenAI streaming event: {}",
                        data,
                        exception
                );
            }
        }

        return contents;
    }


    private String extractData(
            String event
    ) {

        StringBuilder data =
                new StringBuilder();

        String[] lines =
                event.split("\\r?\\n");

        for (String line : lines) {

            if (!line.startsWith("data:")) {
                continue;
            }

            String value =
                    line.substring(
                            5
                    ).trim();

            if (!value.isEmpty()) {

                if (data.length() > 0) {
                    data.append("\n");
                }

                data.append(value);
            }
        }

        return data.toString();
    }
}
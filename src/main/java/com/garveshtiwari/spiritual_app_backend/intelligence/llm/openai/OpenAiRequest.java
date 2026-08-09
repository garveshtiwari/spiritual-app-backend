package com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiRequest {

    private String model;

    private List<Message> messages;

    @JsonProperty("max_completion_tokens")
    private Integer maxCompletionTokens;

    private Boolean stream;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {

        private String role;

        private String content;
    }
}
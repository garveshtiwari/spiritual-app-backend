package com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OpenAiResponse {

    private List<Choice> choices;

    private Usage usage;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Choice {

        private Message message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Message {

        private String role;

        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Usage {

        @JsonProperty("prompt_tokens")
        private Integer promptTokens;

        @JsonProperty("completion_tokens")
        private Integer completionTokens;

        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }
}
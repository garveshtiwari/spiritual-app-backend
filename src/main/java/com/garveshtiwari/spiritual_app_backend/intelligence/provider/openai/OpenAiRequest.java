package com.garveshtiwari.spiritual_app_backend
        .intelligence.provider.openai;

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
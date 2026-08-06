package com.garveshtiwari.spiritual_app_backend.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ConversationResponse {

    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
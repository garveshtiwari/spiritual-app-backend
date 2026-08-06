package com.garveshtiwari.spiritual_app_backend.chat.dto;

import com.garveshtiwari.spiritual_app_backend.common.enums.SenderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatMessageResponse {

    private Long id;

    private SenderType senderType;

    private String message;

    private LocalDateTime createdAt;
}
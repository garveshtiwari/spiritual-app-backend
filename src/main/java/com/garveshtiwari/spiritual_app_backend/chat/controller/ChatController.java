package com.garveshtiwari.spiritual_app_backend.chat.controller;

import com.garveshtiwari.spiritual_app_backend.chat.dto.ChatMessageRequest;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ChatMessageResponse;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ConversationRequest;
import com.garveshtiwari.spiritual_app_backend.chat.dto.ConversationResponse;
import com.garveshtiwari.spiritual_app_backend.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/conversations")
    @ResponseStatus(HttpStatus.CREATED)
    public ConversationResponse createConversation(
            @RequestBody ConversationRequest request
    ) {

        return chatService.createConversation(
                request
        );
    }

    @GetMapping("/conversations")
    public List<ConversationResponse> getConversations() {

        return chatService.getConversations();
    }

    @GetMapping(
            "/conversations/{conversationId}/messages"
    )
    public List<ChatMessageResponse> getMessages(
            @PathVariable Long conversationId
    ) {

        return chatService.getMessages(
                conversationId
        );
    }

    @PostMapping(
            "/conversations/{conversationId}/messages"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ChatMessageResponse sendMessage(
            @PathVariable Long conversationId,
            @RequestBody ChatMessageRequest request
    ) {

        return chatService.sendMessage(
                conversationId,
                request
        );
    }

    @DeleteMapping(
            "/conversations/{conversationId}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConversation(
            @PathVariable Long conversationId
    ) {

        chatService.deleteConversation(
                conversationId
        );
    }
}
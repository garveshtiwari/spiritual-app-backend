package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.entity;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.Conversation;
import com.garveshtiwari.spiritual_app_backend
        .chat.entity.ChatMessage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "conversation_memory"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationMemory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "conversation_id",
            nullable = false,
            unique = true
    )
    private Conversation conversation;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String summary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "last_summarized_message_id"
    )
    private ChatMessage lastSummarizedMessage;

    @Column(
            name = "summary_version",
            nullable = false
    )
    private Integer summaryVersion;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;
}
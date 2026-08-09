package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.entity;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryCategory;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryImportance;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemorySource;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryStatus;
import com.garveshtiwari.spiritual_app_backend
        .user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "long_term_memories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LongTermMemory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String content;

    @Column(
            columnDefinition = "TEXT"
    )
    private String embedding;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemoryCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemoryImportance importance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemoryStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemorySource source;

    @Column(nullable = false)
    private Integer confidence;

    @Column(nullable = false)
    private Boolean pinned;

    private LocalDateTime validUntil;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastAccessedAt;

    @Column(nullable = false)
    private Integer accessCount;

    /**
     * Progress percentage (0-100).
     *
     * Used only for memories that represent
     * long-running goals/projects.
     *
     * For other memory categories this value
     * remains 0.
     */
    @Column(nullable = false)
    private Integer progress;
}
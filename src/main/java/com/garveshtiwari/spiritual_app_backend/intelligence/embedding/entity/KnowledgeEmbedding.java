package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.entity;

import com.garveshtiwari.spiritual_app_backend.common.enums.KnowledgeSource;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "knowledge_embeddings",
        indexes = {
                @Index(
                        name = "idx_document",
                        columnList = "document_source,document_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeEmbedding {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "document_source",
            nullable = false
    )
    private KnowledgeSource documentSource;

    @Column(
            name = "document_id",
            nullable = false
    )
    private Long documentId;

    @Column(nullable = false)
    private String title;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String content;

    @Column(nullable = false)
    private String language;

    /**
     * pgvector column
     */
    @Column(
            columnDefinition = "vector(1536)",
            nullable = false
    )
    private float[] embedding;

    /**
     * JSON metadata
     */
    @Column(
            columnDefinition = "TEXT"
    )
    private String metadata;

    @Column(
            nullable = false
    )
    private LocalDateTime createdAt;
}
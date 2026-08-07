package com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.repository;

import com.garveshtiwari.spiritual_app_backend.common.enums.KnowledgeSource;
import com.garveshtiwari.spiritual_app_backend.intelligence.embedding.entity.KnowledgeEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KnowledgeEmbeddingRepository
        extends JpaRepository<KnowledgeEmbedding, Long> {

    Optional<KnowledgeEmbedding> findByDocumentSourceAndDocumentId(
            KnowledgeSource documentSource,
            Long documentId
    );

    List<KnowledgeEmbedding> findByDocumentSource(
            KnowledgeSource documentSource
    );

    boolean existsByDocumentSourceAndDocumentId(
            KnowledgeSource documentSource,
            Long documentId
    );

    @Query(
            value = """
                SELECT *
                FROM knowledge_embeddings
                ORDER BY embedding <=> CAST(:embedding AS vector)
                LIMIT :limit
                """,
            nativeQuery = true
    )
    List<KnowledgeEmbedding> findMostSimilar(
            @Param("embedding")
            String embedding,

            @Param("limit")
            Integer limit
    );
}
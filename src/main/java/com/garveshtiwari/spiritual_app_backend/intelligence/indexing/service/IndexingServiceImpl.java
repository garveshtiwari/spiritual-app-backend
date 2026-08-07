package com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.service;

import org.springframework.transaction.annotation.Transactional;
import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.book.repository.VerseRepository;
import com.garveshtiwari.spiritual_app_backend.common.enums.KnowledgeSource;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend.intelligence.embedding.dto.EmbeddingResult;
import com.garveshtiwari.spiritual_app_backend.intelligence.embedding.entity.KnowledgeEmbedding;
import com.garveshtiwari.spiritual_app_backend.intelligence.embedding.repository.KnowledgeEmbeddingRepository;
import com.garveshtiwari.spiritual_app_backend.intelligence.embedding.service.EmbeddingService;
import com.garveshtiwari.spiritual_app_backend.intelligence.indexing.builder.EmbeddingContentBuilder;
import com.garveshtiwari.spiritual_app_backend.intelligence.indexing.builder.MetadataBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.dto.IndexingResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl
        implements IndexingService {

    private final VerseRepository verseRepository;

    private final KnowledgeEmbeddingRepository
            knowledgeEmbeddingRepository;

    private final EmbeddingService
            embeddingService;

    private final EmbeddingContentBuilder
            contentBuilder;

    private final MetadataBuilder
            metadataBuilder;

    @Override
    @Transactional
    public IndexingResponse indexAllVerses() {

        int indexed = 0;

        int skipped = 0;

        List<Verse> verses =
                verseRepository.findAll();

        for (Verse verse : verses) {

            boolean exists =
                    knowledgeEmbeddingRepository
                            .existsByDocumentSourceAndDocumentId(
                                    KnowledgeSource.VERSE,
                                    verse.getId()
                            );

            if (exists) {

                skipped++;

                continue;
            }

            KnowledgeEmbedding embedding =
                    buildEmbedding(verse);

            knowledgeEmbeddingRepository.save(
                    embedding
            );

            indexed++;
        }

        return IndexingResponse
                .builder()
                .message(
                        "Indexing completed successfully."
                )
                .indexedCount(
                        indexed
                )
                .skippedCount(
                        skipped
                )
                .build();
    }

    @Override
    @Transactional
    public IndexingResponse indexVerse(
            Long verseId
    ) {

        Verse verse = verseRepository
                .findById(verseId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Verse not found."
                        )
                );

        if (knowledgeEmbeddingRepository
                .existsByDocumentSourceAndDocumentId(
                        KnowledgeSource.VERSE,
                        verseId
                )) {

            return IndexingResponse
                    .builder()
                    .message("Verse is already indexed.")
                    .verseId(verseId)
                    .build();
        }

        KnowledgeEmbedding embedding =
                buildEmbedding(verse);

        knowledgeEmbeddingRepository.save(
                embedding
        );

        return IndexingResponse
                .builder()
                .message("Verse indexed successfully.")
                .verseId(verseId)
                .build();
    }

    @Override
    public void reindexAllVerses() {

    }

    private KnowledgeEmbedding buildEmbedding(
            Verse verse
    ) {

        String content =
                contentBuilder.buildForVerse(
                        verse
                );

        EmbeddingResult embedding =
                embeddingService.generateEmbedding(
                        content
                );

        return KnowledgeEmbedding
                .builder()
                .documentSource(
                        KnowledgeSource.VERSE
                )
                .documentId(
                        verse.getId()
                )
                .title(
                        verse.getChapter()
                                .getBook()
                                .getName()
                                + " "
                                + verse.getChapter()
                                .getChapterNumber()
                                + ":"
                                + verse.getVerseNumber()
                )
                .content(content)
                .language(
                        verse.getChapter()
                                .getBook()
                                .getLanguage()
                                .name()
                )
                .embedding(
                        embedding.getVector()
                )
                .metadata(
                        metadataBuilder
                                .buildForVerse(
                                        verse
                                )
                )
                .createdAt(
                        LocalDateTime.now()
                )
                .build();
    }
}
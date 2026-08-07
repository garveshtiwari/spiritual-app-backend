package com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garveshtiwari.spiritual_app_backend.book.entity.Book;
import com.garveshtiwari.spiritual_app_backend.book.entity.Chapter;
import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.common.exception.AiException;
import com.garveshtiwari.spiritual_app_backend.intelligence.indexing.dto.EmbeddingMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetadataBuilderImpl
        implements MetadataBuilder {

    private final ObjectMapper objectMapper;

    @Override
    public String buildForVerse(
            Verse verse
    ) {

        Chapter chapter = verse.getChapter();

        Book book = chapter.getBook();

        EmbeddingMetadata metadata =
                EmbeddingMetadata.builder()
                        .book(
                                book.getName()
                        )
                        .chapter(
                                chapter.getChapterNumber()
                        )
                        .verse(
                                verse.getVerseNumber()
                        )
                        .language(
                                book.getLanguage().name()
                        )
                        .category(
                                book.getCategory()
                        )
                        .build();

        try {

            return objectMapper.writeValueAsString(
                    metadata
            );

        } catch (JsonProcessingException ex) {

            throw new AiException(
                    "Unable to build metadata."
            );
        }
    }
}
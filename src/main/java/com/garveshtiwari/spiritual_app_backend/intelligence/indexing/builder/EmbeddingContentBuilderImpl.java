package com.garveshtiwari.spiritual_app_backend.intelligence.indexing.builder;

import com.garveshtiwari.spiritual_app_backend.book.entity.Book;
import com.garveshtiwari.spiritual_app_backend.book.entity.Chapter;
import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingContentBuilderImpl
        implements EmbeddingContentBuilder {

    @Override
    public String buildForVerse(
            Verse verse
    ) {

        Chapter chapter = verse.getChapter();

        Book book = chapter.getBook();

        return """
                Scripture:
                %s

                Chapter:
                %d

                Chapter Title:
                %s

                Verse:
                %d

                Original Sanskrit:
                %s

                Transliteration:
                %s

                Translation:
                %s

                Explanation:
                %s
                """.formatted(
                nullSafe(book.getName()),
                chapter.getChapterNumber(),
                nullSafe(chapter.getTitle()),
                verse.getVerseNumber(),
                nullSafe(verse.getOriginalText()),
                nullSafe(verse.getTransliteration()),
                nullSafe(verse.getTranslation()),
                nullSafe(verse.getExplanation())
        );
    }

    private String nullSafe(
            String value
    ) {

        return value == null
                ? ""
                : value;
    }
}
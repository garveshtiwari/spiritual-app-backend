package com.garveshtiwari.spiritual_app_backend.history.mapper;

import com.garveshtiwari.spiritual_app_backend.book.entity.Chapter;
import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.history.dto.ReadingHistoryResponse;
import com.garveshtiwari.spiritual_app_backend.history.entity.ReadingHistory;
import org.springframework.stereotype.Component;

@Component
public class ReadingHistoryMapper {

    public ReadingHistoryResponse toResponse(
            ReadingHistory history
    ) {

        Verse verse = history.getVerse();

        Chapter chapter = verse.getChapter();

        return ReadingHistoryResponse.builder()
                .id(history.getId())
                .verseId(verse.getId())
                .verseNumber(
                        verse.getVerseNumber()
                )
                .chapterNumber(
                        chapter.getChapterNumber()
                )
                .bookId(
                        chapter.getBook().getId()
                )
                .bookName(
                        chapter.getBook().getName()
                )
                .openedAt(
                        history.getOpenedAt()
                )
                .durationInSeconds(
                        history.getDurationInSeconds()
                )
                .build();
    }
}
package com.garveshtiwari.spiritual_app_backend.journal.mapper;

import com.garveshtiwari.spiritual_app_backend.book.entity.Chapter;
import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.journal.dto.JournalResponse;
import com.garveshtiwari.spiritual_app_backend.journal.entity.JournalEntry;
import org.springframework.stereotype.Component;

@Component
public class JournalMapper {

    public JournalResponse toResponse(
            JournalEntry journalEntry
    ) {

        JournalResponse.JournalResponseBuilder builder =
                JournalResponse.builder()
                        .id(journalEntry.getId())
                        .title(journalEntry.getTitle())
                        .content(journalEntry.getContent())
                        .mood(journalEntry.getMood());

        if (journalEntry.getVerse() != null) {

            Verse verse = journalEntry.getVerse();

            Chapter chapter = verse.getChapter();

            builder.verseId(verse.getId())
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
                    );
        }

        return builder.build();
    }
}
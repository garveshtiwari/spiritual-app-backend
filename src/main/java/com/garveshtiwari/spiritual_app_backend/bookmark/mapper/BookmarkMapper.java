package com.garveshtiwari.spiritual_app_backend.bookmark.mapper;

import com.garveshtiwari.spiritual_app_backend.book.entity.Chapter;
import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.bookmark.dto.BookmarkResponse;
import com.garveshtiwari.spiritual_app_backend.bookmark.entity.Bookmark;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

    public BookmarkResponse toResponse(
            Bookmark bookmark
    ) {

        Verse verse = bookmark.getVerse();

        Chapter chapter = verse.getChapter();

        return BookmarkResponse.builder()
                .id(bookmark.getId())
                .verseId(verse.getId())
                .verseNumber(verse.getVerseNumber())
                .chapterNumber(
                        chapter.getChapterNumber()
                )
                .bookId(
                        chapter.getBook().getId()
                )
                .bookName(
                        chapter.getBook().getName()
                )
                .note(bookmark.getNote())
                .build();
    }
}
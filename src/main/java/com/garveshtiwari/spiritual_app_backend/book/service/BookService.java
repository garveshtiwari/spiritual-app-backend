package com.garveshtiwari.spiritual_app_backend.book.service;

import com.garveshtiwari.spiritual_app_backend.book.dto.BookResponse;
import com.garveshtiwari.spiritual_app_backend.book.dto.ChapterResponse;
import com.garveshtiwari.spiritual_app_backend.book.dto.VerseResponse;
import com.garveshtiwari.spiritual_app_backend.common.enums.AppLanguage;

import java.util.List;

public interface BookService {

    List<BookResponse> getAllBooks();

    BookResponse getBookById(
            Long bookId
    );

    BookResponse getBookBySlug(
            String slug
    );

    List<BookResponse> getBooksByLanguage(
            AppLanguage language
    );

    List<ChapterResponse> getChapters(
            Long bookId
    );

    ChapterResponse getChapter(
            Long bookId,
            Integer chapterNumber
    );

    List<VerseResponse> getVerses(
            Long bookId,
            Integer chapterNumber
    );

    VerseResponse getVerse(
            Long bookId,
            Integer chapterNumber,
            Integer verseNumber
    );
}
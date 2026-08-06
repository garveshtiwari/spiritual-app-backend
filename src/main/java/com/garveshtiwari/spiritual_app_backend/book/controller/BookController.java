package com.garveshtiwari.spiritual_app_backend.book.controller;

import com.garveshtiwari.spiritual_app_backend.book.dto.BookResponse;
import com.garveshtiwari.spiritual_app_backend.book.dto.ChapterResponse;
import com.garveshtiwari.spiritual_app_backend.book.dto.VerseResponse;
import com.garveshtiwari.spiritual_app_backend.book.service.BookService;
import com.garveshtiwari.spiritual_app_backend.common.enums.AppLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookResponse> getAllBooks() {

        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public BookResponse getBookById(
            @PathVariable Long bookId
    ) {

        return bookService.getBookById(bookId);
    }

    @GetMapping("/slug/{slug}")
    public BookResponse getBookBySlug(
            @PathVariable String slug
    ) {

        return bookService.getBookBySlug(slug);
    }

    @GetMapping("/language/{language}")
    public List<BookResponse> getBooksByLanguage(
            @PathVariable AppLanguage language
    ) {

        return bookService.getBooksByLanguage(language);
    }

    @GetMapping("/{bookId}/chapters")
    public List<ChapterResponse> getChapters(
            @PathVariable Long bookId
    ) {

        return bookService.getChapters(bookId);
    }

    @GetMapping("/{bookId}/chapters/{chapterNumber}")
    public ChapterResponse getChapter(
            @PathVariable Long bookId,
            @PathVariable Integer chapterNumber
    ) {

        return bookService.getChapter(
                bookId,
                chapterNumber
        );
    }

    @GetMapping(
            "/{bookId}/chapters/{chapterNumber}/verses"
    )
    public List<VerseResponse> getVerses(
            @PathVariable Long bookId,
            @PathVariable Integer chapterNumber
    ) {

        return bookService.getVerses(
                bookId,
                chapterNumber
        );
    }

    @GetMapping(
            "/{bookId}/chapters/{chapterNumber}/verses/{verseNumber}"
    )
    public VerseResponse getVerse(
            @PathVariable Long bookId,
            @PathVariable Integer chapterNumber,
            @PathVariable Integer verseNumber
    ) {

        return bookService.getVerse(
                bookId,
                chapterNumber,
                verseNumber
        );
    }
}
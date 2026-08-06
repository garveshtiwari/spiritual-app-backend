package com.garveshtiwari.spiritual_app_backend.book.service;

import com.garveshtiwari.spiritual_app_backend.book.dto.BookResponse;
import com.garveshtiwari.spiritual_app_backend.book.dto.ChapterResponse;
import com.garveshtiwari.spiritual_app_backend.book.dto.VerseResponse;
import com.garveshtiwari.spiritual_app_backend.book.entity.Book;
import com.garveshtiwari.spiritual_app_backend.book.entity.Chapter;
import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.book.mapper.BookMapper;
import com.garveshtiwari.spiritual_app_backend.book.mapper.ChapterMapper;
import com.garveshtiwari.spiritual_app_backend.book.mapper.VerseMapper;
import com.garveshtiwari.spiritual_app_backend.book.repository.BookRepository;
import com.garveshtiwari.spiritual_app_backend.book.repository.ChapterRepository;
import com.garveshtiwari.spiritual_app_backend.book.repository.VerseRepository;
import com.garveshtiwari.spiritual_app_backend.common.enums.AppLanguage;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final ChapterRepository chapterRepository;

    private final VerseRepository verseRepository;

    private final BookMapper bookMapper;

    private final ChapterMapper chapterMapper;

    private final VerseMapper verseMapper;

    @Override
    public List<BookResponse> getAllBooks() {

        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Override
    public BookResponse getBookById(
            Long bookId
    ) {

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Book not found."
                        )
                );

        return bookMapper.toResponse(book);
    }

    @Override
    public BookResponse getBookBySlug(
            String slug
    ) {

        Book book = bookRepository
                .findBySlug(slug)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Book not found."
                        )
                );

        return bookMapper.toResponse(book);
    }

    @Override
    public List<BookResponse> getBooksByLanguage(
            AppLanguage language
    ) {

        return bookRepository
                .findByLanguage(language)
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Override
    public List<ChapterResponse> getChapters(
            Long bookId
    ) {

        return chapterRepository
                .findByBookIdOrderByChapterNumberAsc(
                        bookId
                )
                .stream()
                .map(chapterMapper::toResponse)
                .toList();
    }

    @Override
    public ChapterResponse getChapter(
            Long bookId,
            Integer chapterNumber
    ) {

        Chapter chapter = chapterRepository
                .findByBookIdAndChapterNumber(
                        bookId,
                        chapterNumber
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Chapter not found."
                        )
                );

        return chapterMapper.toResponse(chapter);
    }

    @Override
    public List<VerseResponse> getVerses(
            Long bookId,
            Integer chapterNumber
    ) {

        Chapter chapter = chapterRepository
                .findByBookIdAndChapterNumber(
                        bookId,
                        chapterNumber
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Chapter not found."
                        )
                );

        return verseRepository
                .findByChapterIdOrderByVerseNumberAsc(
                        chapter.getId()
                )
                .stream()
                .map(verseMapper::toResponse)
                .toList();
    }

    @Override
    public VerseResponse getVerse(
            Long bookId,
            Integer chapterNumber,
            Integer verseNumber
    ) {

        Chapter chapter = chapterRepository
                .findByBookIdAndChapterNumber(
                        bookId,
                        chapterNumber
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Chapter not found."
                        )
                );

        Verse verse = verseRepository
                .findByChapterIdAndVerseNumber(
                        chapter.getId(),
                        verseNumber
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Verse not found."
                        )
                );

        return verseMapper.toResponse(verse);
    }
}
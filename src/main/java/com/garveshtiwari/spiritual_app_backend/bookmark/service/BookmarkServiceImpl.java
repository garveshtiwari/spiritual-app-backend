package com.garveshtiwari.spiritual_app_backend.bookmark.service;

import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.book.repository.VerseRepository;
import com.garveshtiwari.spiritual_app_backend.bookmark.dto.BookmarkRequest;
import com.garveshtiwari.spiritual_app_backend.bookmark.dto.BookmarkResponse;
import com.garveshtiwari.spiritual_app_backend.bookmark.entity.Bookmark;
import com.garveshtiwari.spiritual_app_backend.bookmark.mapper.BookmarkMapper;
import com.garveshtiwari.spiritual_app_backend.bookmark.repository.BookmarkRepository;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl
        implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    private final VerseRepository verseRepository;

    private final UserRepository userRepository;

    private final BookmarkMapper bookmarkMapper;

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found."
                        )
                );
    }

    @Override
    public BookmarkResponse createBookmark(
            BookmarkRequest request
    ) {

        User user = getCurrentUser();

        if (bookmarkRepository.existsByUserIdAndVerseId(
                user.getId(),
                request.getVerseId()
        )) {
            throw new IllegalArgumentException(
                    "Bookmark already exists."
            );
        }

        Verse verse = verseRepository
                .findById(request.getVerseId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Verse not found."
                        )
                );

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .verse(verse)
                .note(request.getNote())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        bookmarkRepository.save(bookmark);

        return bookmarkMapper.toResponse(bookmark);
    }

    @Override
    public List<BookmarkResponse> getBookmarks() {

        User user = getCurrentUser();

        return bookmarkRepository
                .findByUserIdOrderByCreatedAtDesc(
                        user.getId()
                )
                .stream()
                .map(bookmarkMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteBookmark(
            Long verseId
    ) {

        User user = getCurrentUser();

        Bookmark bookmark = bookmarkRepository
                .findByUserIdAndVerseId(
                        user.getId(),
                        verseId
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Bookmark not found."
                        )
                );

        bookmarkRepository.delete(bookmark);
    }
}
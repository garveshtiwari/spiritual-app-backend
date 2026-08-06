package com.garveshtiwari.spiritual_app_backend.bookmark.controller;

import com.garveshtiwari.spiritual_app_backend.bookmark.dto.BookmarkRequest;
import com.garveshtiwari.spiritual_app_backend.bookmark.dto.BookmarkResponse;
import com.garveshtiwari.spiritual_app_backend.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public BookmarkResponse createBookmark(
            @RequestBody BookmarkRequest request
    ) {

        return bookmarkService.createBookmark(
                request
        );
    }

    @GetMapping
    public List<BookmarkResponse> getBookmarks() {

        return bookmarkService.getBookmarks();
    }

    @DeleteMapping("/{verseId}")
    public void deleteBookmark(
            @PathVariable Long verseId
    ) {

        bookmarkService.deleteBookmark(
                verseId
        );
    }
}
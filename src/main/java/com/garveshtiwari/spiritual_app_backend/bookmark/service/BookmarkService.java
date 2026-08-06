package com.garveshtiwari.spiritual_app_backend.bookmark.service;

import com.garveshtiwari.spiritual_app_backend.bookmark.dto.BookmarkRequest;
import com.garveshtiwari.spiritual_app_backend.bookmark.dto.BookmarkResponse;

import java.util.List;

public interface BookmarkService {

    BookmarkResponse createBookmark(
            BookmarkRequest request
    );

    List<BookmarkResponse> getBookmarks();

    void deleteBookmark(
            Long verseId
    );
}
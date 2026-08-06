package com.garveshtiwari.spiritual_app_backend.bookmark.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookmarkResponse {

    private Long id;

    private Long verseId;

    private Integer verseNumber;

    private Integer chapterNumber;

    private Long bookId;

    private String bookName;

    private String note;
}
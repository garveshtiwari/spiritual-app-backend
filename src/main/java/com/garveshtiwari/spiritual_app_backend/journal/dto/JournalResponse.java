package com.garveshtiwari.spiritual_app_backend.journal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JournalResponse {

    private Long id;

    private Long verseId;

    private Integer verseNumber;

    private Integer chapterNumber;

    private Long bookId;

    private String bookName;

    private String title;

    private String content;

    private String mood;
}
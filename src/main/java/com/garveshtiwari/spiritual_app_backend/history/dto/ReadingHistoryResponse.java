package com.garveshtiwari.spiritual_app_backend.history.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReadingHistoryResponse {

    private Long id;

    private Long verseId;

    private Integer verseNumber;

    private Integer chapterNumber;

    private Long bookId;

    private String bookName;

    private LocalDateTime openedAt;

    private Long durationInSeconds;
}
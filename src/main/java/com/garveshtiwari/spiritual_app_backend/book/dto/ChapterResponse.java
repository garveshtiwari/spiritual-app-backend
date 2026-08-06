package com.garveshtiwari.spiritual_app_backend.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChapterResponse {

    private Long id;

    private Integer chapterNumber;

    private String title;

    private String summary;
}
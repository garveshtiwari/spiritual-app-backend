package com.garveshtiwari.spiritual_app_backend.bookmark.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkRequest {

    private Long verseId;

    private String note;
}
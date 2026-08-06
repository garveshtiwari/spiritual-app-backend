package com.garveshtiwari.spiritual_app_backend.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerseResponse {

    private Long id;

    private Integer verseNumber;

    private String originalText;

    private String transliteration;

    private String translation;

    private String explanation;

    private String audioUrl;
}
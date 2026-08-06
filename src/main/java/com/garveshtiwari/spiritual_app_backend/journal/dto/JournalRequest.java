package com.garveshtiwari.spiritual_app_backend.journal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JournalRequest {

    private Long verseId;

    private String title;

    private String content;

    private String mood;
}
package com.garveshtiwari.spiritual_app_backend.history.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadingHistoryRequest {

    private Long verseId;

    private Long durationInSeconds;
}
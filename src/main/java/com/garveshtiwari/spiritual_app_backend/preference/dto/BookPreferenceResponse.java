package com.garveshtiwari.spiritual_app_backend.preference.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookPreferenceResponse {

    private Long id;

    private Long bookId;

    private String bookName;

    private String slug;
}
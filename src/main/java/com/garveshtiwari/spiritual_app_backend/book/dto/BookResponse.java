package com.garveshtiwari.spiritual_app_backend.book.dto;

import com.garveshtiwari.spiritual_app_backend.common.enums.AppLanguage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookResponse {

    private Long id;

    private String name;

    private String slug;

    private String description;

    private AppLanguage language;

    private String author;

    private String category;

    private String coverImageUrl;
}
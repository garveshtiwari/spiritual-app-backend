package com.garveshtiwari.spiritual_app_backend.intelligence.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Citation {

    private Long documentId;

    private String source;

    private String book;

    private Integer chapter;

    private Integer verse;

    private String title;
}
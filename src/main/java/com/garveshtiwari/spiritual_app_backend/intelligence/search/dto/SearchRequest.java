package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto;

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
public class SearchRequest {

    private String query;

    private Integer limit;
}
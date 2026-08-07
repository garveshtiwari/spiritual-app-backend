package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchResponse {

    private List<SearchResult> results;
}
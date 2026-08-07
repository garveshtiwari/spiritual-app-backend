package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

    private String query;

    private Integer limit;
}
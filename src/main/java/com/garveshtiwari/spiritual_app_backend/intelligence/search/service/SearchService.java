package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResponse;

public interface SearchService {

    SearchResponse search(
            SearchRequest request
    );
}
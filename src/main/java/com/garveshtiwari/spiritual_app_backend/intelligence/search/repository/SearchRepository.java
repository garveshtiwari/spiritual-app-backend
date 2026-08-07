package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.repository;

import com.garveshtiwari.spiritual_app_backend.intelligence.search.dto.SearchResult;

import java.util.List;

public interface SearchRepository {

    List<SearchResult> search(
            String vector,
            Integer limit
    );
}
package com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.ranking;

import com.garveshtiwari.spiritual_app_backend.intelligence.search.dto.SearchResult;

import java.util.List;

public interface PersonalizedRankingService {

    List<SearchResult> rank(
            Long userId,
            List<SearchResult> searchResults,
            Integer limit
    );
}
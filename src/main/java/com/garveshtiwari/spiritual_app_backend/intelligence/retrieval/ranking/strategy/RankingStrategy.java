package com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.ranking.strategy;

import com.garveshtiwari.spiritual_app_backend.intelligence.search.dto.SearchResult;

import java.util.List;

public interface RankingStrategy {

    List<SearchResult> rank(
            Long userId,
            List<SearchResult> results
    );
}
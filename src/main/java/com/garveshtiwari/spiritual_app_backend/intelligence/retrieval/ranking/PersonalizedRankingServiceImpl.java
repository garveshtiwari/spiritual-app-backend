package com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.ranking;

import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.ranking.strategy.RankingStrategy;
import com.garveshtiwari.spiritual_app_backend.intelligence.search.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalizedRankingServiceImpl
        implements PersonalizedRankingService {

    private final List<RankingStrategy> strategies;

    @Override
    public List<SearchResult> rank(
            Long userId,
            List<SearchResult> searchResults,
            Integer limit
    ) {

        List<SearchResult> rankedResults =
                searchResults;

        for (RankingStrategy strategy : strategies) {

            rankedResults =
                    strategy.rank(
                            userId,
                            rankedResults
                    );
        }

        return rankedResults.stream()
                .limit(limit)
                .toList();
    }
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.ranking.strategy;

import com.garveshtiwari.spiritual_app_backend
        .history.entity.ReadingHistory;
import com.garveshtiwari.spiritual_app_backend
        .history.repository.ReadingHistoryRepository;
import com.garveshtiwari.spiritual_app_backend.intelligence.config.RankingProperties;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReadingHistoryRankingStrategy
        implements RankingStrategy {

    private final RankingProperties
            rankingProperties;

    private final ReadingHistoryRepository
            readingHistoryRepository;

    @Override
    public List<SearchResult> rank(
            Long userId,
            List<SearchResult> results
    ) {

        if (results.isEmpty()) {
            return results;
        }

        List<Long> verseIds =
                results.stream()
                        .map(SearchResult::getDocumentId)
                        .toList();

        Map<Long, Long> durationMap =
                readingHistoryRepository
                        .findByUserIdAndVerseIdIn(
                                userId,
                                verseIds
                        )
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        history ->
                                                history
                                                        .getVerse()
                                                        .getId(),
                                        ReadingHistory::getDurationInSeconds,
                                        Long::max
                                )
                        );

        results.forEach(result -> {

            Long duration =
                    durationMap.get(
                            result.getDocumentId()
                    );

            if (duration == null) {
                return;
            }

            double normalized =
                    Math.min(
                            duration,
                            rankingProperties
                                    .getReadingHistoryMaxDuration()
                    ) / (double)
                            rankingProperties
                                    .getReadingHistoryMaxDuration();

            result.setRankingScore(
                    result.getRankingScore()
                            + normalized
                            * rankingProperties
                            .getReadingHistoryMaxBoost()
            );
        });

        return results.stream()
                .sorted(
                        Comparator.comparing(
                                SearchResult::getRankingScore
                        ).reversed()
                )
                .toList();
    }
}
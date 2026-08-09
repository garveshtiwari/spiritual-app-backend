package com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.ranking.strategy;

import com.garveshtiwari.spiritual_app_backend.bookmark.entity.Bookmark;
import com.garveshtiwari.spiritual_app_backend.bookmark.repository.BookmarkRepository;
import com.garveshtiwari.spiritual_app_backend.intelligence.config.RankingProperties;
import com.garveshtiwari.spiritual_app_backend.intelligence.search.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookmarkRankingStrategy
        implements RankingStrategy {

    private final RankingProperties
            rankingProperties;

    private final BookmarkRepository
            bookmarkRepository;

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

        Set<Long> bookmarkedVerseIds =
                bookmarkRepository
                        .findByUserIdAndVerseIdIn(
                                userId,
                                verseIds
                        )
                        .stream()
                        .map(Bookmark::getVerse)
                        .map(verse -> verse.getId())
                        .collect(Collectors.toSet());

        results.forEach(result -> {

            if (bookmarkedVerseIds.contains(
                    result.getDocumentId()
            )) {

                result.setRankingScore(
                        result.getRankingScore()
                                + rankingProperties
                                .getBookmarkBoost()
                );
            }
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
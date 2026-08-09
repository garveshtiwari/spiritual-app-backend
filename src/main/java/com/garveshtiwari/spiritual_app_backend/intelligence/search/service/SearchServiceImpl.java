package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.common.util.VectorUtils;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.dto.EmbeddingResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.service.EmbeddingService;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl
        implements SearchService {

    private final EmbeddingService
            embeddingService;

    private final SearchRepository
            searchRepository;


    @Override
    public SearchResponse search(
            SearchRequest request
    ) {

        long totalStart =
                System.currentTimeMillis();


        /*
         * =========================================================
         * 1. GENERATE QUERY EMBEDDING
         * =========================================================
         */

        long embeddingStart =
                System.currentTimeMillis();

        EmbeddingResult embedding =
                embeddingService.generateEmbedding(
                        request.getQuery()
                );

        long embeddingTime =
                System.currentTimeMillis()
                        - embeddingStart;

        log.info(
                "SEARCH EMBEDDING TIME: {} ms",
                embeddingTime
        );


        /*
         * =========================================================
         * 2. CONVERT VECTOR
         * =========================================================
         */

        long vectorStart =
                System.currentTimeMillis();

        String vector =
                VectorUtils.toPgVector(
                        embedding.getVector()
                );

        long vectorTime =
                System.currentTimeMillis()
                        - vectorStart;

        log.info(
                "VECTOR CONVERSION TIME: {} ms",
                vectorTime
        );


        /*
         * =========================================================
         * 3. VECTOR DATABASE SEARCH
         * =========================================================
         */

        Integer limit =
                request.getLimit() == null
                        ? 5
                        : request.getLimit();

        long databaseStart =
                System.currentTimeMillis();

        List<SearchResult> results =
                searchRepository.search(
                        vector,
                        request.getPreferredBookIds(),
                        limit
                );

        long databaseTime =
                System.currentTimeMillis()
                        - databaseStart;

        log.info(
                "VECTOR DATABASE SEARCH TIME: {} ms",
                databaseTime
        );


        /*
         * =========================================================
         * TOTAL SEARCH TIME
         * =========================================================
         */

        long totalTime =
                System.currentTimeMillis()
                        - totalStart;

        log.info(
                "TOTAL SEARCH TIME: {} ms",
                totalTime
        );


        log.info(
                "SEARCH RESULTS COUNT: {}",
                results == null
                        ? 0
                        : results.size()
        );


        return SearchResponse
                .builder()
                .results(results)
                .build();
    }
}
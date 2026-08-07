package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.common.util.VectorUtils;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.dto.EmbeddingResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.entity.KnowledgeEmbedding;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.repository.KnowledgeEmbeddingRepository;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.embedding.service.EmbeddingService;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResult;
import com.garveshtiwari.spiritual_app_backend.intelligence.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Integer limit =
                request.getLimit() == null
                        ? 5
                        : request.getLimit();

        EmbeddingResult embedding =
                embeddingService.generateEmbedding(
                        request.getQuery()
                );

        String vector =
                VectorUtils.toPgVector(
                        embedding.getVector()
                );

        List<SearchResult> results =
                searchRepository.search(
                        vector,
                        limit
                );

        return SearchResponse
                .builder()
                .results(results)
                .build();
    }
}
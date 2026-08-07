package com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievedDocument;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievalServiceImpl
        implements RetrievalService {

    private static final Integer DEFAULT_LIMIT = 5;

    private final SearchService searchService;

    @Override
    public RetrievalResponse retrieve(
            String question
    ) {

        SearchResponse response =
                searchService.search(
                        SearchRequest
                                .builder()
                                .query(question)
                                .limit(DEFAULT_LIMIT)
                                .build()
                );

        List<RetrievedDocument> documents =
                response.getResults()
                        .stream()
                        .map(this::toRetrievedDocument)
                        .toList();

        return RetrievalResponse
                .builder()
                .documents(documents)
                .build();
    }

    private RetrievedDocument toRetrievedDocument(
            SearchResult result
    ) {

        return RetrievedDocument
                .builder()
                .documentId(result.getDocumentId())
                .title(result.getTitle())
                .content(result.getContent())
                .metadata(result.getMetadata())
                .similarity(result.getSimilarity())
                .build();
    }
}
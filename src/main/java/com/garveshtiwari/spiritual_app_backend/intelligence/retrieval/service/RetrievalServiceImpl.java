package com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.service;

import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto.RetrievalRequest;
import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto.RetrievalResponse;
import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto.RetrievedDocument;
import com.garveshtiwari.spiritual_app_backend.intelligence.search.dto.SearchRequest;
import com.garveshtiwari.spiritual_app_backend.intelligence.search.dto.SearchResponse;
import com.garveshtiwari.spiritual_app_backend.intelligence.search.dto.SearchResult;
import com.garveshtiwari.spiritual_app_backend.intelligence.search.service.SearchService;
import com.garveshtiwari.spiritual_app_backend.preference.repository.UserPreferredBookRepository;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievalServiceImpl
        implements RetrievalService {

    private final SearchService searchService;

    private final UserRepository userRepository;

    private final UserPreferredBookRepository
            userPreferredBookRepository;

    @Override
    public RetrievalResponse retrieve(
            RetrievalRequest request
    ) {

        User user = getCurrentUser();

        List<Long> preferredBookIds =
                userPreferredBookRepository
                        .findByUserPreferenceUserId(
                                user.getId()
                        )
                        .stream()
                        .map(userPreferredBook ->
                                userPreferredBook
                                        .getBook()
                                        .getId()
                        )
                        .toList();

        SearchResponse response =
                searchService.search(
                        SearchRequest
                                .builder()
                                .query(
                                        request.getQuestion()
                                )
                                .limit(
                                        request.getLimit()
                                )
                                .preferredBookIds(
                                        preferredBookIds
                                )
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

    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found."
                        )
                );
    }

    private RetrievedDocument toRetrievedDocument(
            SearchResult result
    ) {

        return RetrievedDocument
                .builder()
                .documentId(
                        result.getDocumentId()
                )
                .title(
                        result.getTitle()
                )
                .content(
                        result.getContent()
                )
                .metadata(
                        result.getMetadata()
                )
                .similarity(
                        result.getSimilarity()
                )
                .build();
    }
}
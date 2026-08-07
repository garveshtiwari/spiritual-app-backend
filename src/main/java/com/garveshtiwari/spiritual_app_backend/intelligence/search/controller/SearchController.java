package com.garveshtiwari.spiritual_app_backend
        .intelligence.search.controller;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.dto.SearchResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService
            searchService;

    @PostMapping
    public SearchResponse search(
            @RequestBody
            SearchRequest request
    ) {

        return searchService.search(
                request
        );
    }
}
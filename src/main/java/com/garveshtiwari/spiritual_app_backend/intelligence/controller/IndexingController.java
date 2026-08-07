package com.garveshtiwari.spiritual_app_backend.intelligence.controller;

import com.garveshtiwari.spiritual_app_backend.intelligence.indexing.service.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.dto.IndexingResponse;

@RestController
@RequestMapping("/api/v1/indexing")
@RequiredArgsConstructor
public class IndexingController {

    private final IndexingService indexingService;

    @PostMapping("/verses/{id}")
    public IndexingResponse indexVerse(
            @PathVariable Long id
    ) {

        return indexingService.indexVerse(id);
    }

    @PostMapping("/verses")
    public IndexingResponse indexAllVerses() {

        return indexingService
                .indexAllVerses();
    }
}

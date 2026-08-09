package com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.controller;

import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto.RetrievalRequest;
import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto.RetrievalResponse;
import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.service.RetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/retrieval")
@RequiredArgsConstructor
public class RetrievalController {

    private final RetrievalService retrievalService;

    @PostMapping
    public RetrievalResponse retrieve(
            @RequestBody RetrievalRequest request
    ) {

        return retrievalService.retrieve(
                request
        );
    }
}
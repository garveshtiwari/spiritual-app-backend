package com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.service;

import reactor.core.publisher.Flux;

public interface AiService {

    String generateResponse(
            String prompt
    );

    Flux<String> generateResponseStream(
            String prompt
    );
}
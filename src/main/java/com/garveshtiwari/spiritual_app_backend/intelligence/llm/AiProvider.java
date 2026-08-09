package com.garveshtiwari.spiritual_app_backend
        .intelligence.llm;

import reactor.core.publisher.Flux;

public interface AiProvider {

    String generateResponse(
            String prompt
    );

    Flux<String> generateResponseStream(
            String prompt
    );
}
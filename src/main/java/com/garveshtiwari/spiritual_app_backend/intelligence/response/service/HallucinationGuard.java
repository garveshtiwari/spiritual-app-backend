package com.garveshtiwari.spiritual_app_backend.intelligence.response.service;

import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto.RetrievalResponse;
import org.springframework.stereotype.Component;

@Component
public class HallucinationGuard {

    private static final double MIN_CONFIDENCE = 0.75;

    public boolean isGrounded(
            RetrievalResponse retrievalResponse
    ) {

        if (retrievalResponse == null
                || retrievalResponse.getDocuments() == null
                || retrievalResponse.getDocuments().isEmpty()) {

            return false;
        }

        double confidence =
                retrievalResponse.getDocuments()
                        .stream()
                        .mapToDouble(document ->
                                document.getSimilarity() == null
                                        ? 0.0
                                        : document.getSimilarity())
                        .average()
                        .orElse(0.0);

        return confidence >= MIN_CONFIDENCE;
    }
}
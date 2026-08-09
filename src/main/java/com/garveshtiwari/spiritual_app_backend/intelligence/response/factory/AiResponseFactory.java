package com.garveshtiwari.spiritual_app_backend.intelligence.response.factory;

import com.garveshtiwari.spiritual_app_backend.intelligence.response.dto.AiResponse;
import com.garveshtiwari.spiritual_app_backend.intelligence.response.dto.Citation;
import com.garveshtiwari.spiritual_app_backend.intelligence.response.dto.ResponseType;
import com.garveshtiwari.spiritual_app_backend.intelligence.response.mapper.CitationMapper;
import com.garveshtiwari.spiritual_app_backend.intelligence.response.service.HallucinationGuard;
import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto.RetrievalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AiResponseFactory {

    private final CitationMapper citationMapper;

    private final HallucinationGuard hallucinationGuard;

    public AiResponse build(
            String answer,
            RetrievalResponse retrievalResponse
    ) {

        List<Citation> citations =
                retrievalResponse.getDocuments()
                        .stream()
                        .map(citationMapper::toCitation)
                        .toList();

        return AiResponse.builder()
                .answer(answer)
                .citations(citations)
                .grounded(
                        hallucinationGuard.isGrounded(
                                retrievalResponse
                        )
                )
                .confidence(calculateConfidence(retrievalResponse))
                .responseType(ResponseType.GUIDANCE)
                .followUpQuestions(List.of())
                .relatedVerseIds(List.of())
                .build();
    }

    private Double calculateConfidence(
            RetrievalResponse retrievalResponse
    ) {

        if (retrievalResponse == null
                || retrievalResponse.getDocuments() == null
                || retrievalResponse.getDocuments().isEmpty()) {

            return 0.0;
        }

        return retrievalResponse.getDocuments()
                .stream()
                .mapToDouble(document ->
                        document.getSimilarity() == null
                                ? 0.0
                                : document.getSimilarity())
                .average()
                .orElse(0.0);
    }
}
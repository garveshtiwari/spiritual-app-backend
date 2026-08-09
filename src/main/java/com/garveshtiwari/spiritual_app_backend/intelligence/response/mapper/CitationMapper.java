package com.garveshtiwari.spiritual_app_backend.intelligence.response.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garveshtiwari.spiritual_app_backend.intelligence.response.dto.Citation;
import com.garveshtiwari.spiritual_app_backend.intelligence.retrieval.dto.RetrievedDocument;
import org.springframework.stereotype.Component;

@Component
public class CitationMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Citation toCitation(
            RetrievedDocument document
    ) {

        try {

            JsonNode metadata =
                    objectMapper.readTree(
                            document.getMetadata()
                    );

            return Citation.builder()
                    .documentId(document.getDocumentId())
                    .source(metadata.path("book").asText())
                    .book(metadata.path("book").asText())
                    .chapter(metadata.path("chapter").asInt())
                    .verse(metadata.path("verse").asInt())
                    .title(document.getTitle())
                    .build();

        } catch (Exception e) {

            return Citation.builder()
                    .documentId(document.getDocumentId())
                    .title(document.getTitle())
                    .build();
        }
    }
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.builder;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievedDocument;
import org.springframework.stereotype.Component;

@Component
public class RetrievedKnowledgeBuilder {

    public String build(
            RetrievalResponse retrievalResponse
    ) {

        if (retrievalResponse == null
                || retrievalResponse.getDocuments() == null
                || retrievalResponse.getDocuments().isEmpty()) {

            return "";
        }

        StringBuilder knowledge =
                new StringBuilder();

        for (RetrievedDocument document
                : retrievalResponse.getDocuments()) {

            knowledge.append("Title: ")
                    .append(document.getTitle())
                    .append("\n");

            knowledge.append("Content:\n")
                    .append(document.getContent())
                    .append("\n\n");
        }

        return knowledge.toString();
    }
}
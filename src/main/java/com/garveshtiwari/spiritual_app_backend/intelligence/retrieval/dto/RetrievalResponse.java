package com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RetrievalResponse {

    private List<RetrievedDocument> documents;
}
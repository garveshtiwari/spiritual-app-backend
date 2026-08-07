package com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalResponse;

public interface RetrievalService {

    RetrievalResponse retrieve(
            String question
    );
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.service;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalResponse;

public interface RetrievalService {

    RetrievalResponse retrieve(
            RetrievalRequest request
    );
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.converter;

import com.fasterxml.jackson.core.type.TypeReference;

public interface AiResponseConverter {

    <T> T convert(
            String response,
            TypeReference<T> typeReference
    );
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.exception.AiResponseParsingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JacksonAiResponseConverter
        implements AiResponseConverter {

    private final ObjectMapper objectMapper;

    @Override
    public <T> T convert(
            String response,
            TypeReference<T> typeReference
    ) {

        try {

            return objectMapper.readValue(
                    response,
                    typeReference
            );

        } catch (Exception exception) {

            throw new AiResponseParsingException(
                    "Failed to convert AI response.",
                    exception
            );
        }
    }
}
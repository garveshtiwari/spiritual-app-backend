package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.converter.AiResponseConverter;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.service.AiService;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto.MemoryExtractionRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto.MemoryExtractionResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.prompt.MemoryExtractionPromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoryExtractionServiceImpl
        implements MemoryExtractionService {

    private final MemoryExtractionPromptBuilder
            promptBuilder;

    private final AiService
            aiService;

    private final AiResponseConverter
            aiResponseConverter;

    @Override
    public List<MemoryExtractionResult> extract(
            MemoryExtractionRequest request
    ) {

        String prompt =
                promptBuilder.buildPrompt(
                        request
                );

        String response =
                aiService.generateResponse(
                        prompt
                );

        return aiResponseConverter.convert(
                response,
                new TypeReference<List<MemoryExtractionResult>>() {
                }
        );
    }
}
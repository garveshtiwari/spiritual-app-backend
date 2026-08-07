package com.garveshtiwari.spiritual_app_backend
        .intelligence.service;

import com.garveshtiwari.spiritual_app_backend.intelligence.llm.AiProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiProviderFactory providerFactory;

    @Override
    public String generateResponse(
            String prompt
    ) {

        return providerFactory
                .getProvider()
                .generateResponse(prompt);
    }
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.llm;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.config.IntelligenceProperties;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.openai.OpenAiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiProviderFactory {

    private final IntelligenceProperties properties;

    private final OpenAiProvider openAiProvider;

    public AiProvider getProvider() {

        return switch (properties.getProvider()) {

            case "openai" -> openAiProvider;

            default -> throw new IllegalArgumentException(
                    "Unsupported AI provider."
            );
        };
    }
}
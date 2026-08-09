package com.garveshtiwari.spiritual_app_backend.intelligence.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        OpenAiProperties.class,
        IntelligenceProperties.class,
        RankingProperties.class,
        MemoryProperties.class
})
public class IntelligenceConfiguration {
}
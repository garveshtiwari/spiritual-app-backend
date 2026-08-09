package com.garveshtiwari.spiritual_app_backend.intelligence.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(
        prefix = "intelligence.memory"
)
public class MemoryProperties {

    /**
     * Number of latest messages that
     * should always remain unsummarized.
     */
    private int recentMessages = 20;

    /**
     * Minimum number of messages before
     * summarization starts.
     */
    private int summaryThreshold = 40;

    /**
     * Current summary prompt version.
     */
    private int summaryVersion = 1;
}
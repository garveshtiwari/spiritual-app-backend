package com.garveshtiwari.spiritual_app_backend.intelligence.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(
        prefix = "intelligence.ranking"
)
public class RankingProperties {

    /**
     * Bookmark boost.
     */
    private double bookmarkBoost = 0.10;

    /**
     * Maximum boost from reading history.
     */
    private double readingHistoryMaxBoost = 0.08;

    /**
     * Reading duration (seconds)
     * after which the maximum boost
     * is applied.
     */
    private long readingHistoryMaxDuration = 300;
}
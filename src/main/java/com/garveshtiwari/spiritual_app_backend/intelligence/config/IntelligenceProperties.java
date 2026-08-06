package com.garveshtiwari.spiritual_app_backend.intelligence.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "intelligence")
public class IntelligenceProperties {

    private String provider;
}
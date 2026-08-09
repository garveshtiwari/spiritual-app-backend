package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.util;

import org.springframework.stereotype.Component;

@Component
public class MemoryNormalizer {

    public String normalize(
            String content
    ) {

        if (content == null) {
            return "";
        }

        return content
                .trim()
                .replaceAll("\\s+", " ")
                .replaceAll("[.]$", "");
    }
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.decision;

public interface MemoryExtractionDecider {

    boolean shouldExtract(
            String conversation
    );
}
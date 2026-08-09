package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.decision;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class MemoryExtractionDeciderImpl
        implements MemoryExtractionDecider {

    private static final int
            MIN_CONVERSATION_LENGTH = 100;

    private static final List<String>
            TRIVIAL_MESSAGES = List.of(

            "hi",
            "hello",
            "hey",
            "thanks",
            "thank you",
            "good morning",
            "good afternoon",
            "good evening",
            "good night",
            "bye"
    );

    @Override
    public boolean shouldExtract(
            String conversation
    ) {

        if (conversation == null) {
            return false;
        }

        conversation =
                conversation.trim();

        if (conversation.isBlank()) {
            return false;
        }

        if (conversation.length()
                < MIN_CONVERSATION_LENGTH) {

            return false;
        }

        String normalized =
                conversation
                        .toLowerCase(
                                Locale.ROOT
                        );

        for (String trivial
                : TRIVIAL_MESSAGES) {

            if (normalized.equals(trivial)
                    || normalized.endsWith(": " + trivial)) {

                return false;
            }
        }

        return true;
    }
}
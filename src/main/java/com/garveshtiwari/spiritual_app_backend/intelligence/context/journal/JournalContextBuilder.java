package com.garveshtiwari.spiritual_app_backend
        .intelligence.context.journal;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import com.garveshtiwari.spiritual_app_backend
        .journal.entity.JournalEntry;
import com.garveshtiwari.spiritual_app_backend
        .journal.repository.JournalEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JournalContextBuilder
        implements ContextBuilder {

    private static final int LIMIT = 5;

    private final JournalEntryRepository
            journalEntryRepository;

    @Override
    public String buildContext(
            Long userId
    ) {

        List<JournalEntry> journalEntries =
                journalEntryRepository
                        .findByUserIdOrderByCreatedAtDesc(
                                userId
                        );

        if (journalEntries.isEmpty()) {
            return "";
        }

        StringBuilder context =
                new StringBuilder();

        context.append("""
                Recent journal entries:

                """);

        int count = Math.min(
                LIMIT,
                journalEntries.size()
        );

        for (int i = 0; i < count; i++) {

            JournalEntry entry =
                    journalEntries.get(i);

            context.append("Title: ")
                    .append(entry.getTitle())
                    .append("\n");

            context.append("Mood: ")
                    .append(entry.getMood())
                    .append("\n");

            if (entry.getVerse() != null) {

                context.append("Verse ID: ")
                        .append(entry.getVerse().getId())
                        .append("\n");
            }

            context.append("Content: ")
                    .append(entry.getContent())
                    .append("\n\n");
        }

        return context.toString();
    }
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.context.history;

import com.garveshtiwari.spiritual_app_backend
        .history.entity.ReadingHistory;
import com.garveshtiwari.spiritual_app_backend
        .history.repository.ReadingHistoryRepository;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoryContextBuilder
        implements ContextBuilder {

    private static final int LIMIT = 5;

    private final ReadingHistoryRepository
            readingHistoryRepository;

    @Override
    public String buildContext(
            Long userId
    ) {

        List<ReadingHistory> historyList =
                readingHistoryRepository
                        .findByUserIdOrderByOpenedAtDesc(
                                userId
                        );

        if (historyList.isEmpty()) {
            return "";
        }

        StringBuilder context =
                new StringBuilder();

        context.append("""
                Recent reading history:

                """);

        int count = Math.min(
                LIMIT,
                historyList.size()
        );

        for (int i = 0; i < count; i++) {

            ReadingHistory history =
                    historyList.get(i);

            context.append(
                    "Verse ID: "
            ).append(
                    history.getVerse().getId()
            ).append(
                    "\n"
            );

            context.append(
                    "Duration (seconds): "
            ).append(
                    history.getDurationInSeconds()
            ).append(
                    "\n\n"
            );
        }

        return context.toString();
    }
}
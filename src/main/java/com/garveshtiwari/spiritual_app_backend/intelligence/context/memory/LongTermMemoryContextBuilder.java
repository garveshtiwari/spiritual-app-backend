package com.garveshtiwari.spiritual_app_backend
        .intelligence.context.memory;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.context.ContextBuilder;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.entity.LongTermMemory;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.service.LongTermMemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LongTermMemoryContextBuilder
        implements ContextBuilder {

    private static final int
            MAX_RELEVANT_MEMORIES = 5;

    private final LongTermMemoryService
            longTermMemoryService;

    @Override
    public String buildContext(
            Long userId,
            Long conversationId,
            String userMessage
    ) {

        if (userMessage == null ||
                userMessage.isBlank()) {

            return "";
        }

        List<LongTermMemory> memories =
                longTermMemoryService
                        .findRelevantMemories(
                                userMessage,
                                MAX_RELEVANT_MEMORIES
                        );

        // TEMPORARY DEBUG LOG
        memories.forEach(memory ->
                System.out.println(
                        "RELEVANT MEMORY -> ID: "
                                + memory.getId()
                                + " | "
                                + memory.getContent()
                )
        );

        if (memories.isEmpty()) {
            return "";
        }

        StringBuilder context =
                new StringBuilder();

        context.append(
                "Relevant Long-Term Memories:\n\n"
        );

        for (LongTermMemory memory :
                memories) {

            context.append(
                    "Memory ID: "
            ).append(
                    memory.getId()
            ).append("\n");

            context.append(
                    "Category: "
            ).append(
                    memory.getCategory()
            ).append("\n");

            context.append(
                    "Content: "
            ).append(
                    memory.getContent()
            ).append("\n\n");
        }

        return context.toString();
    }
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.service;

import com.garveshtiwari.spiritual_app_backend
        .common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryCategory;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryStatus;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.dto.LongTermMemoryResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.entity.LongTermMemory;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.mapper.LongTermMemoryMapper;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.repository.LongTermMemoryRepository;
import com.garveshtiwari.spiritual_app_backend
        .user.entity.User;
import com.garveshtiwari.spiritual_app_backend
        .user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LongTermMemoryServiceImpl
        implements LongTermMemoryService {

    private final LongTermMemoryRepository
            repository;

    private final LongTermMemoryMapper
            mapper;

    private final UserRepository
            userRepository;

    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found."
                        )
                );
    }

    @Override
    public LongTermMemory store(
            LongTermMemory memory
    ) {

        return repository.save(
                memory
        );
    }

    @Override
    public List<LongTermMemoryResponse> findAll() {

        User user =
                getCurrentUser();

        return repository
                .findByUserIdOrderByUpdatedAtDesc(
                        user.getId()
                )
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<LongTermMemoryResponse> findActive() {

        User user =
                getCurrentUser();

        return repository
                .findByUserIdAndStatusOrderByUpdatedAtDesc(
                        user.getId(),
                        MemoryStatus.ACTIVE
                )
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public LongTermMemory findById(
            Long id
    ) {

        User user =
                getCurrentUser();

        return repository
                .findByIdAndUserId(
                        id,
                        user.getId()
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Memory not found."
                        )
                );
    }

    @Override
    public void markAccessed(
            LongTermMemory memory
    ) {

        memory.setLastAccessedAt(
                LocalDateTime.now()
        );

        memory.setAccessCount(
                memory.getAccessCount() + 1
        );

        repository.save(
                memory
        );
    }

    @Override
    public String getExistingMemories() {

        User user =
                getCurrentUser();

        List<LongTermMemory> memories =
                repository.findByUserIdAndStatus(
                        user.getId(),
                        MemoryStatus.ACTIVE
                );

        if (memories.isEmpty()) {
            return "";
        }

        StringBuilder builder =
                new StringBuilder();

        for (LongTermMemory memory :
                memories) {

            builder.append(
                    "ID: "
            ).append(
                    memory.getId()
            ).append(
                    "\n"
            );

            builder.append(
                    "Category: "
            ).append(
                    memory.getCategory()
            ).append(
                    "\n"
            );

            builder.append(
                    "Importance: "
            ).append(
                    memory.getImportance()
            ).append(
                    "\n"
            );

            builder.append(
                    "Confidence: "
            ).append(
                    memory.getConfidence()
            ).append(
                    "\n"
            );

            builder.append(
                    "Content: "
            ).append(
                    memory.getContent()
            ).append(
                    "\n\n"
            );
        }

        return builder.toString();
    }

    @Override
    public List<LongTermMemory> findRelevantMemories(
            String query,
            int limit
    ) {

        User user =
                getCurrentUser();

        if (query == null ||
                query.isBlank() ||
                limit <= 0) {

            return List.of();
        }

        List<LongTermMemory> memories =
                repository
                        .findByUserIdAndStatusOrderByUpdatedAtDesc(
                                user.getId(),
                                MemoryStatus.ACTIVE
                        );

        if (memories.isEmpty()) {
            return List.of();
        }

        Set<String> queryTokens =
                tokenize(query);

        if (queryTokens.isEmpty()) {
            return List.of();
        }

        Map<LongTermMemory, Integer> scores =
                new HashMap<>();

        for (LongTermMemory memory :
                memories) {

            int score =
                    calculateRelevanceScore(
                            memory,
                            queryTokens,
                            query
                    );

            if (score > 0) {
                scores.put(
                        memory,
                        score
                );
            }
        }

        return scores
                .entrySet()
                .stream()
                .sorted(
                        Map.Entry
                                .<LongTermMemory, Integer>
                                        comparingByValue()
                                .reversed()
                                .thenComparing(
                                        entry ->
                                                entry.getKey()
                                                        .getUpdatedAt(),
                                        Comparator
                                                .nullsLast(
                                                        Comparator
                                                                .reverseOrder()
                                                )
                                )
                )
                .limit(limit)
                .map(
                        Map.Entry::getKey
                )
                .collect(
                        Collectors.toList()
                );
    }

    private int calculateRelevanceScore(
            LongTermMemory memory,
            Set<String> queryTokens,
            String query
    ) {

        String content =
                memory.getContent() == null
                        ? ""
                        : memory.getContent();

        String category =
                memory.getCategory() == null
                        ? ""
                        : memory.getCategory().name();

        Set<String> memoryTokens =
                tokenize(
                        content
                );

        int score = 0;

        /*
         * Direct content overlap.
         */
        for (String token :
                queryTokens) {

            if (memoryTokens.contains(token)) {

                score += 5;
            }
        }

        /*
         * Category relevance.
         */
        score +=
                calculateCategoryBoost(
                        memory.getCategory(),
                        queryTokens
                );

        /*
         * Exact phrase match.
         */
        if (!query.isBlank() &&
                content
                        .toLowerCase(Locale.ROOT)
                        .contains(
                                query
                                        .toLowerCase(Locale.ROOT)
                        )) {

            score += 15;
        }

        /*
         * Important memories receive a small boost,
         * but importance alone must never make an
         * unrelated memory relevant.
         */
        if (score > 0 &&
                memory.getImportance() != null) {

            switch (memory.getImportance()) {

                case HIGH ->
                        score += 2;

                case MEDIUM ->
                        score += 1;

                default -> {
                    // No additional boost.
                }
            }
        }

        return score;
    }

    private int calculateCategoryBoost(
            MemoryCategory category,
            Set<String> queryTokens
    ) {

        if (category == null) {
            return 0;
        }

        return switch (category) {

            case GOAL ->

                    containsAny(
                            queryTokens,
                            Set.of(
                                    "goal",
                                    "career",
                                    "job",
                                    "work",
                                    "interview",
                                    "interviews",
                                    "target",
                                    "future",
                                    "plan",
                                    "planning"
                            )
                    ) ? 4 : 0;

            case LOCATION ->

                    containsAny(
                            queryTokens,
                            Set.of(
                                    "live",
                                    "living",
                                    "location",
                                    "city",
                                    "move",
                                    "moved",
                                    "moving",
                                    "home"
                            )
                    ) ? 4 : 0;

            case SPIRITUAL ->

                    containsAny(
                            queryTokens,
                            Set.of(
                                    "spiritual",
                                    "spirituality",
                                    "gita",
                                    "god",
                                    "prayer",
                                    "meditation",
                                    "dharma",
                                    "karma",
                                    "shloka",
                                    "scripture"
                            )
                    ) ? 4 : 0;

            case PREFERENCE ->

                    containsAny(
                            queryTokens,
                            Set.of(
                                    "like",
                                    "love",
                                    "hobby",
                                    "hobbies",
                                    "interest",
                                    "interests",
                                    "learn",
                                    "learning",
                                    "photography",
                                    "guitar"
                            )
                    ) ? 4 : 0;

            case LIFE_EVENT ->

                    containsAny(
                            queryTokens,
                            Set.of(
                                    "moved",
                                    "moving",
                                    "started",
                                    "graduated",
                                    "married",
                                    "changed",
                                    "change",
                                    "recently",
                                    "last",
                                    "month"
                            )
                    ) ? 4 : 0;

            default -> 0;
        };
    }

    private boolean containsAny(
            Set<String> tokens,
            Set<String> candidates
    ) {

        for (String candidate :
                candidates) {

            if (tokens.contains(candidate)) {
                return true;
            }
        }

        return false;
    }

    private Set<String> tokenize(
            String text
    ) {

        if (text == null ||
                text.isBlank()) {

            return Set.of();
        }

        return Arrays
                .stream(
                        text
                                .toLowerCase(
                                        Locale.ROOT
                                )
                                .replaceAll(
                                        "[^a-z0-9]+",
                                        " "
                                )
                                .trim()
                                .split("\\s+")
                )
                .filter(
                        token ->
                                token.length() >= 3
                )
                .filter(
                        token ->
                                !isStopWord(token)
                )
                .collect(
                        Collectors.toSet()
                );
    }

    private boolean isStopWord(
            String token
    ) {

        return Set.of(
                "the",
                "and",
                "for",
                "are",
                "was",
                "were",
                "has",
                "have",
                "had",
                "with",
                "that",
                "this",
                "from",
                "into",
                "about",
                "user",
                "wants",
                "want",
                "they",
                "their",
                "there",
                "here",
                "now",
                "currently",
                "seriously"
        ).contains(
                token
        );
    }
}
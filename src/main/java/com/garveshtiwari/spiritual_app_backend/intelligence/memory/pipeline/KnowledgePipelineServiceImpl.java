package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.pipeline;

import com.garveshtiwari.spiritual_app_backend
        .common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.conversation.provider.ConversationProvider;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.common.MemoryAction;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto.MemoryExtractionRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto.MemoryExtractionResult;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.mapper.MemoryExtractionMapper;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.service.MemoryExtractionService;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.entity.LongTermMemory;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.longterm.service.LongTermMemoryService;
import com.garveshtiwari.spiritual_app_backend
        .user.entity.User;
import com.garveshtiwari.spiritual_app_backend
        .user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgePipelineServiceImpl
        implements KnowledgePipelineService {

    private final ConversationProvider
            conversationProvider;

    private final MemoryExtractionService
            memoryExtractionService;

    private final MemoryExtractionMapper
            memoryExtractionMapper;

    private final LongTermMemoryService
            longTermMemoryService;

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
    public void processInteraction(
            Long conversationId
    ) {

        long pipelineStart =
                System.currentTimeMillis();

        log.info(
                "Starting knowledge pipeline for conversation {}",
                conversationId
        );

        User user =
                getCurrentUser();


        /*
         * ---------------------------------------------------------
         * GET CONVERSATION
         * ---------------------------------------------------------
         */

        long conversationStart =
                System.currentTimeMillis();

        String conversation =
                conversationProvider.getConversation(
                        conversationId
                );

        log.info(
                "CONVERSATION PROVIDER TIME: {} ms",
                System.currentTimeMillis()
                        - conversationStart
        );


        if (conversation == null ||
                conversation.isBlank()) {

            log.info(
                    "Conversation is empty. Skipping knowledge extraction."
            );

            return;
        }


        /*
         * ---------------------------------------------------------
         * GET EXISTING MEMORIES
         * ---------------------------------------------------------
         */

        long existingMemoryStart =
                System.currentTimeMillis();

        String existingMemories =
                longTermMemoryService
                        .getExistingMemories();

        log.info(
                "GET EXISTING MEMORIES TIME: {} ms",
                System.currentTimeMillis()
                        - existingMemoryStart
        );


        /*
         * ---------------------------------------------------------
         * BUILD EXTRACTION REQUEST
         * ---------------------------------------------------------
         */

        MemoryExtractionRequest request =
                MemoryExtractionRequest
                        .builder()
                        .existingMemories(
                                existingMemories
                        )
                        .conversationSummary("")
                        .recentConversation(
                                conversation
                        )
                        .build();


        /*
         * ---------------------------------------------------------
         * MEMORY EXTRACTION LLM
         * ---------------------------------------------------------
         */

        long extractionStart =
                System.currentTimeMillis();

        List<MemoryExtractionResult> results =
                memoryExtractionService.extract(
                        request
                );

        long extractionTime =
                System.currentTimeMillis()
                        - extractionStart;

        log.info(
                "MEMORY EXTRACTION TIME: {} ms",
                extractionTime
        );


        if (results == null ||
                results.isEmpty()) {

            log.info(
                    "No memories extracted from conversation {}",
                    conversationId
            );

            log.info(
                    "TOTAL KNOWLEDGE PIPELINE TIME: {} ms",
                    System.currentTimeMillis()
                            - pipelineStart
            );

            return;
        }


        /*
         * ---------------------------------------------------------
         * PROCESS EXTRACTED MEMORIES
         * ---------------------------------------------------------
         */

        long memoryProcessingStart =
                System.currentTimeMillis();

        for (MemoryExtractionResult result :
                results) {

            if (result == null) {
                continue;
            }

            MemoryAction action =
                    result.getAction();

            if (action == null) {

                log.warn(
                        "Memory extraction returned no action. " +
                                "Skipping memory: {}",
                        result.getMemory()
                );

                continue;
            }

            switch (action) {

                case IGNORE -> {

                    log.info(
                            "Ignoring extracted memory: {}",
                            result.getMemory()
                    );
                }

                case CREATE -> {

                    createMemory(
                            user,
                            result
                    );
                }

                case UPDATE -> {

                    updateMemory(
                            user,
                            result
                    );
                }
            }
        }

        log.info(
                "MEMORY PROCESSING TIME: {} ms",
                System.currentTimeMillis()
                        - memoryProcessingStart
        );


        /*
         * ---------------------------------------------------------
         * TOTAL PIPELINE TIME
         * ---------------------------------------------------------
         */

        log.info(
                "TOTAL KNOWLEDGE PIPELINE TIME: {} ms",
                System.currentTimeMillis()
                        - pipelineStart
        );

        log.info(
                "Knowledge pipeline completed successfully " +
                        "for conversation {}",
                conversationId
        );
    }


    private void createMemory(
            User user,
            MemoryExtractionResult result
    ) {

        LongTermMemory memory =
                memoryExtractionMapper.toEntity(
                        result
                );

        memory.setUser(
                user
        );

        longTermMemoryService.store(
                memory
        );

        log.info(
                "Created new long-term memory: {}",
                result.getMemory()
        );
    }


    private void updateMemory(
            User user,
            MemoryExtractionResult result
    ) {

        if (result.getExistingMemoryId() == null) {

            log.warn(
                    "UPDATE action received without " +
                            "existingMemoryId. " +
                            "Skipping memory: {}",
                    result.getMemory()
            );

            return;
        }

        LongTermMemory existingMemory =
                longTermMemoryService.findById(
                        result.getExistingMemoryId()
                );

        /*
         * findById() is already user-scoped, but keep this explicit
         * ownership check so the pipeline never accidentally updates
         * a memory belonging to another user.
         */
        if (existingMemory.getUser() == null ||
                !existingMemory.getUser()
                        .getId()
                        .equals(user.getId())) {

            log.warn(
                    "Attempted to update memory belonging " +
                            "to another user. " +
                            "Skipping memory ID: {}",
                    result.getExistingMemoryId()
            );

            return;
        }

        existingMemory.setContent(
                result.getMemory()
        );

        existingMemory.setCategory(
                result.getCategory()
        );

        if (result.getConfidence() != null) {

            existingMemory.setConfidence(
                    (int) Math.round(
                            result.getConfidence() * 100
                    )
            );
        }

        existingMemory.setUpdatedAt(
                LocalDateTime.now()
        );

        longTermMemoryService.store(
                existingMemory
        );

        log.info(
                "Updated long-term memory ID {}: {}",
                existingMemory.getId(),
                result.getMemory()
        );
    }
}
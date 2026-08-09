package com.garveshtiwari.spiritual_app_backend
        .intelligence.chat.service;

import com.garveshtiwari.spiritual_app_backend
        .chat.entity.Conversation;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.llm.service.AiService;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.service.PromptService;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalRequest;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.dto.RetrievalResponse;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.retrieval.service.RetrievalService;
import com.garveshtiwari.spiritual_app_backend
        .user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatAiServiceImpl
        implements ChatAiService {

    private final RetrievalService
            retrievalService;

    private final PromptService
            promptService;

    private final AiService
            aiService;


    @Override
    public String generateResponse(
            User user,
            Conversation conversation,
            String userMessage
    ) {

        /*
         * =========================================================
         * 1. RETRIEVAL DECISION
         * =========================================================
         *
         * We should not perform an embedding/vector search for
         * every conversation message.
         *
         * Retrieval is primarily useful when the user is asking
         * about scripture, verses, Krishna, Gita teachings,
         * spiritual concepts, etc.
         */

        long retrievalStart =
                System.currentTimeMillis();

        RetrievalResponse retrievalResponse;

        boolean shouldRetrieve =
                shouldRetrieveKnowledge(
                        userMessage
                );

        if (shouldRetrieve) {

            retrievalResponse =
                    retrievalService.retrieve(
                            RetrievalRequest
                                    .builder()
                                    .userId(
                                            user.getId()
                                    )
                                    .question(
                                            userMessage
                                    )
                                    .build()
                    );

            log.info(
                    "RETRIEVAL EXECUTED"
            );

        } else {

            retrievalResponse = null;

            log.info(
                    "RETRIEVAL SKIPPED"
            );
        }

        log.info(
                "RETRIEVAL DECISION: {}",
                shouldRetrieve
        );

        log.info(
                "RETRIEVAL TIME: {} ms",
                System.currentTimeMillis()
                        - retrievalStart
        );


        /*
         * =========================================================
         * 2. BUILD PROMPT
         * =========================================================
         */

        long promptStart =
                System.currentTimeMillis();

        String prompt =
                promptService.buildPrompt(
                        user.getId(),
                        conversation.getId(),
                        userMessage,
                        retrievalResponse
                );

        log.info(
                "PROMPT BUILD TIME: {} ms",
                System.currentTimeMillis()
                        - promptStart
        );


        /*
         * =========================================================
         * 3. GENERATE AI RESPONSE
         * =========================================================
         */

        long aiStart =
                System.currentTimeMillis();

        String response =
                aiService.generateResponse(
                        prompt
                );

        log.info(
                "LLM RESPONSE TIME: {} ms",
                System.currentTimeMillis()
                        - aiStart
        );

        return response;
    }


    @Override
    public Flux<String> generateResponseStream(
            User user,
            Conversation conversation,
            String userMessage
    ) {

        /*
         * =========================================================
         * 1. RETRIEVAL DECISION
         * =========================================================
         */

        long retrievalStart =
                System.currentTimeMillis();

        RetrievalResponse retrievalResponse;

        boolean shouldRetrieve =
                shouldRetrieveKnowledge(
                        userMessage
                );

        if (shouldRetrieve) {

            retrievalResponse =
                    retrievalService.retrieve(
                            RetrievalRequest
                                    .builder()
                                    .userId(
                                            user.getId()
                                    )
                                    .question(
                                            userMessage
                                    )
                                    .build()
                    );

            log.info(
                    "STREAM RETRIEVAL EXECUTED"
            );

        } else {

            retrievalResponse = null;

            log.info(
                    "STREAM RETRIEVAL SKIPPED"
            );
        }

        log.info(
                "STREAM RETRIEVAL DECISION: {}",
                shouldRetrieve
        );

        log.info(
                "STREAM RETRIEVAL TIME: {} ms",
                System.currentTimeMillis()
                        - retrievalStart
        );


        /*
         * =========================================================
         * 2. BUILD PROMPT
         * =========================================================
         */

        long promptStart =
                System.currentTimeMillis();

        String prompt =
                promptService.buildPrompt(
                        user.getId(),
                        conversation.getId(),
                        userMessage,
                        retrievalResponse
                );

        log.info(
                "STREAM PROMPT BUILD TIME: {} ms",
                System.currentTimeMillis()
                        - promptStart
        );


        /*
         * =========================================================
         * 3. STREAM AI RESPONSE
         * =========================================================
         */

        return aiService
                .generateResponseStream(
                        prompt
                );
    }


    /*
     * =============================================================
     * RETRIEVAL DECISION
     * =============================================================
     *
     * This intentionally runs locally.
     *
     * We do NOT call another LLM here because doing so would add
     * another network request and increase latency.
     *
     * The detector is deliberately conservative:
     *
     * - Explicit scripture questions -> retrieve
     * - Explicit Gita questions      -> retrieve
     * - Explicit spiritual teaching  -> retrieve
     * - Normal life conversation     -> skip
     */
    private boolean shouldRetrieveKnowledge(
            String userMessage
    ) {

        if (userMessage == null ||
                userMessage.isBlank()) {

            return false;
        }

        String message =
                userMessage
                        .toLowerCase(Locale.ROOT)
                        .trim();


        /*
         * =========================================================
         * SCRIPTURE / GITA REFERENCES
         * =========================================================
         */

        String[] scriptureKeywords = {

                "bhagavad gita",
                "bhagavad-gita",
                "gita",
                "geeta",

                "krishna",
                "arjuna",

                "verse",
                "verses",
                "shloka",
                "shlok",

                "chapter",

                "scripture",
                "scriptures",

                "according to the gita",
                "according to gita",

                "gita says",
                "gita teaches",

                "krishna says",
                "krishna teaches",

                "what does the gita say",
                "what does gita say",

                "what does krishna say",

                "which verse",
                "which shloka",

                "explain this verse",
                "explain this shloka"
        };


        for (String keyword :
                scriptureKeywords) {

            if (message.contains(keyword)) {
                return true;
            }
        }


        /*
         * =========================================================
         * SPIRITUAL TEACHINGS / CONCEPTS
         * =========================================================
         */

        String[] spiritualKeywords = {

                "dharma",
                "karma yoga",
                "karma-yoga",

                "nishkama karma",
                "nishkam karma",

                "detachment",
                "non-attachment",

                "attachment to results",
                "attachment to outcome",

                "fruits of action",
                "fruit of action",

                "duty according to gita",
                "purpose according to gita",

                "spiritual teaching",
                "spiritual teachings",

                "spiritual lesson",

                "teachings of krishna",
                "teachings of the gita",

                "meaning of this verse",
                "meaning of this shloka"
        };


        for (String keyword :
                spiritualKeywords) {

            if (message.contains(keyword)) {
                return true;
            }
        }


        /*
         * =========================================================
         * EXPLICIT REQUEST FOR SCRIPTURE-BASED GUIDANCE
         * =========================================================
         */

        if (containsAny(
                message,
                "gita reference",
                "gita perspective",
                "gita teaching",
                "gita wisdom",
                "gita guidance",
                "scriptural guidance",
                "scripture-based",
                "scripture based"
        )) {

            return true;
        }


        return false;
    }


    private boolean containsAny(
            String message,
            String... keywords
    ) {

        for (String keyword :
                keywords) {

            if (message.contains(keyword)) {
                return true;
            }
        }

        return false;
    }
}
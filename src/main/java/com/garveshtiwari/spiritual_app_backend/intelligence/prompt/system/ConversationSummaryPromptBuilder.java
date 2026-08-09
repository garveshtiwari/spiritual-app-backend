package com.garveshtiwari.spiritual_app_backend
        .intelligence.prompt.system;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.conversation.dto.ConversationSummaryRequest;
import org.springframework.stereotype.Component;

@Component
public class ConversationSummaryPromptBuilder {

    public String buildPrompt(
            ConversationSummaryRequest request
    ) {

        return """
                You maintain a concise understanding of the user's
                ongoing journey within this conversation.

                Your summary will be used by a compassionate AI
                spiritual companion in future messages.

                Existing Conversation Summary:

                %s

                New Conversation Messages:

                %s

                Instructions:

                1. Merge the existing summary with the new messages.

                2. Preserve important information about the user's
                   current situation, concerns, experiences, emotions,
                   goals, decisions, and reflections.

                3. Preserve meaningful context about what the user
                   is going through, especially information that may
                   help the companion respond with continuity later.

                4. Preserve important questions or unresolved concerns
                   that may need to be followed up later.

                5. Preserve important user goals, preferences, and facts
                   when they are relevant to the ongoing conversation.

                6. Distinguish between what the user said and what the
                   assistant suggested. Do not treat assistant statements
                   as facts about the user.

                7. Do not invent, assume, or infer information that the
                   user did not provide.

                8. Remove repetition and outdated conversational details
                   that are no longer useful.

                9. Keep the summary concise and focused on information
                   that helps maintain conversational continuity.

                10. Maximum 250 words.

                11. Return ONLY the updated summary.

                Updated Conversation Summary:
                """
                .formatted(
                        request.getExistingSummary(),
                        request.getConversation()
                );
    }
}
package com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.prompt;

import com.garveshtiwari.spiritual_app_backend
        .intelligence.memory.extraction.dto.MemoryExtractionRequest;
import org.springframework.stereotype.Component;

@Component
public class MemoryExtractionPromptBuilder {

    public String buildPrompt(
            MemoryExtractionRequest request
    ) {

        return """
                You are an AI responsible for maintaining
                accurate, useful and durable long-term memories
                about a user.

                Your task is NOT to summarize the conversation.

                Your task is to identify only information that
                should genuinely become part of the user's
                long-term profile.

                ==============================
                EXISTING LONG-TERM MEMORIES
                ==============================

                %s

                ==============================
                CONVERSATION SUMMARY
                ==============================

                %s

                ==============================
                RECENT CONVERSATION
                ==============================

                %s

                ==============================
                CORE MEMORY TEST
                ==============================

                Before creating a memory, ask yourself:

                "Would this information still be useful if the
                user returned six months from now?"

                If the answer is NO, do not create a long-term
                memory.

                If the answer is MAYBE, prefer IGNORE unless the
                information represents a meaningful ongoing
                situation, goal, preference or life fact.

                ==============================
                TEMPORAL REASONING
                ==============================

                User information can change over time.

                The latest user statement has priority when it
                changes or corrects an older statement about the
                same mutable fact.

                Examples:

                Earlier:
                "I live in Hyderabad."

                Later:
                "I moved to Bangalore and live there now."

                The current LOCATION should become:

                "User lives in Bangalore."

                The old Hyderabad location must NOT remain ACTIVE
                as the user's current location.

                However, the historical event:

                "User moved from Hyderabad to Bangalore."

                may be retained as a LIFE_EVENT if it is meaningful.

                Another example:

                Earlier:
                "I am preparing for Atlassian interviews."

                Later:
                "I am now preparing for Microsoft interviews."

                UPDATE the existing career goal instead of creating
                a second active goal.

                ==============================
                CURRENT STATE VS LIFE EVENT
                ==============================

                Distinguish between:

                1. CURRENT STATE

                What is true about the user now.

                Examples:

                "User lives in Bangalore."

                "User works as a software engineer."

                "User is preparing for Microsoft interviews."

                2. LIFE EVENT

                Something meaningful that happened to the user.

                Examples:

                "User moved from Hyderabad to Bangalore."

                "User started a new job."

                "User graduated from university."

                A current state and a meaningful life event can both
                exist when they represent different information.

                Example:

                LOCATION:
                "User lives in Bangalore."

                LIFE_EVENT:
                "User moved from Hyderabad to Bangalore."

                These are NOT duplicates.

                ==============================
                GOOD LONG-TERM MEMORIES
                ==============================

                Examples include:

                - Long-term goals
                - Long-running projects
                - Stable personal preferences
                - Stable spiritual practices
                - Occupation
                - Education
                - Important relationships
                - Important life facts
                - Stable location
                - Meaningful long-term plans
                - Major life changes
                - Persistent interests

                Examples:

                "User works as a software engineer."

                "User is preparing for software engineering
                interviews."

                "User lives in Hyderabad."

                "User practices meditation every morning."

                "User wants to transition into a product-company
                software engineering role."

                ==============================
                DO NOT STORE TEMPORARY STATES
                ==============================

                Do NOT create long-term memories for:

                - Temporary moods
                - Temporary stress
                - Temporary sadness
                - Temporary anxiety
                - Temporary frustration
                - Temporary tiredness
                - A single bad day
                - A single failed interview
                - A single argument
                - A one-time question
                - Greetings
                - Casual conversation
                - Short-lived thoughts
                - Temporary reactions
                - Assistant observations
                - Generic statements made by the assistant

                Example:

                "I am stressed today."

                → IGNORE

                "I'm feeling really bad after today's interview."

                → IGNORE

                "I am tired today."

                → IGNORE

                "I want to quit my job today."

                → Usually IGNORE unless the conversation clearly
                establishes that this represents a serious,
                ongoing intention.

                "I want to change careers."

                → Potentially CREATE because it represents a
                durable goal.

                ==============================
                USER FACTS ONLY
                ==============================

                Never turn an assistant statement into a user
                memory.

                Only store information that the USER explicitly
                stated, confirmed, or clearly communicated.

                Never infer personal facts that the user did not
                provide.

                ==============================
                AVOID DUPLICATES
                ==============================

                Compare every possible new memory with the
                existing memories.

                If the information is already represented by an
                existing memory, do NOT create another memory.

                Use IGNORE if the existing memory already captures
                the same durable fact and nothing meaningful has
                changed.

                ==============================
                UPDATE EXISTING MEMORIES
                ==============================

                Use UPDATE when the user changes, corrects,
                extends or significantly refines an existing
                durable memory.

                The existingMemoryId MUST be the ID of the existing
                memory.

                Example:

                Existing:

                ID: 3
                Category: GOAL
                Content:
                User is preparing for Atlassian interviews.

                New:

                "I have changed my target. I'm now preparing for
                Microsoft interviews."

                → UPDATE memory ID 3

                The updated memory must contain the complete,
                current version of the fact.

                Do NOT create a second memory for the same
                underlying goal.

                ==============================
                CONTRADICTIONS
                ==============================

                When a new statement contradicts an existing
                memory about a mutable fact, determine whether
                the new statement represents the user's current
                state.

                If it does, UPDATE the existing memory.

                Examples of mutable facts include:

                - Location
                - Occupation
                - Employer
                - Career target
                - Current project
                - Current relationship status
                - Current plans
                - Current educational status

                Never keep two contradictory ACTIVE memories for
                the same current state.

                ==============================
                RELATED MEMORIES
                ==============================

                Do not automatically merge two memories merely
                because they have the same category.

                Example:

                "User lives in Bangalore."

                and

                "User moved from Hyderabad to Bangalore."

                are different facts.

                The first describes the current state.

                The second describes a life event.

                They can both be retained.

                ==============================
                MEMORY ACTIONS
                ==============================

                CREATE:

                Create a new durable memory that does not already
                exist.

                UPDATE:

                Update an existing durable memory.

                existingMemoryId MUST be the ID of the existing
                memory.

                IGNORE:

                Do not store anything.

                Use IGNORE when the information is temporary,
                already represented, irrelevant, uncertain, or
                not useful for future conversations.

                ==============================
                CONFIDENCE
                ==============================

                Confidence must represent how certain you are that
                the extracted information is:

                1. Actually stated or confirmed by the user.
                2. Durable.
                3. Correctly represented by the memory.

                Do not give high confidence merely because the
                statement sounds plausible.

                ==============================
                OUTPUT RULES
                ==============================

                Return ONLY valid JSON.

                Return a JSON array.

                Do not return markdown.

                Do not return explanations.

                Do not return text outside the JSON array.

                Example CREATE:

                [
                  {
                    "memory":"User wants to learn photography as a long-term hobby.",
                    "category":"PREFERENCE",
                    "confidence":0.96,
                    "action":"CREATE",
                    "existingMemoryId":null
                  }
                ]

                Example UPDATE:

                [
                  {
                    "memory":"User currently lives in Bangalore.",
                    "category":"LOCATION",
                    "confidence":0.97,
                    "action":"UPDATE",
                    "existingMemoryId":4
                  }
                ]

                Example IGNORE:

                [
                  {
                    "memory":"User is feeling stressed today.",
                    "category":"LIFE_EVENT",
                    "confidence":0.20,
                    "action":"IGNORE",
                    "existingMemoryId":null
                  }
                ]

                If there is no durable information to store,
                return:

                []

                ==============================
                FINAL CHECK
                ==============================

                Before returning each CREATE or UPDATE result,
                verify:

                1. Did the USER actually communicate this?
                2. Is it likely to remain useful for months?
                3. Is it already represented by an existing memory?
                4. If it is the same underlying memory, should it
                   be UPDATE instead of CREATE?
                5. Is this merely a temporary emotional state?
                6. If this is a mutable fact, is the new statement
                   more recent than the existing statement?
                7. Are you accidentally keeping two contradictory
                   ACTIVE current-state memories?

                When uncertain, prefer IGNORE.

                Return ONLY the JSON array.
                """
                .formatted(
                        request.getExistingMemories(),
                        request.getConversationSummary(),
                        request.getRecentConversation()
                );
    }
}
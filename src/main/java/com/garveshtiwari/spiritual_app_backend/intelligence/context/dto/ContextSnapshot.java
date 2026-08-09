package com.garveshtiwari.spiritual_app_backend.intelligence.context.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents everything the AI knows about the
 * current user for a single request.
 *
 * This object is not persisted.
 * It is built fresh for every AI request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextSnapshot {

    /**
     * User identifier.
     */
    private Long userId;

    /**
     * User preferred books.
     */
    private List<Long> preferredBookIds;

    /**
     * User bookmarked verses.
     */
    private List<Long> bookmarkedVerseIds;

    /**
     * Recently read verses.
     */
    private List<Long> recentlyReadVerseIds;

    /**
     * Current conversation summary.
     * (Phase 6)
     */
    private String conversationSummary;

    /**
     * Journal summary.
     * (Future)
     */
    private String journalSummary;

    /**
     * Preferred application language.
     */
    private String language;
}
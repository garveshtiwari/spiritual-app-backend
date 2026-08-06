package com.garveshtiwari.spiritual_app_backend.journal.service;

import com.garveshtiwari.spiritual_app_backend.journal.dto.JournalRequest;
import com.garveshtiwari.spiritual_app_backend.journal.dto.JournalResponse;

import java.util.List;

public interface JournalService {

    JournalResponse createJournalEntry(
            JournalRequest request
    );

    List<JournalResponse> getJournalEntries();

    JournalResponse getJournalEntry(
            Long journalId
    );

    JournalResponse updateJournalEntry(
            Long journalId,
            JournalRequest request
    );

    void deleteJournalEntry(
            Long journalId
    );
}
package com.garveshtiwari.spiritual_app_backend.journal.repository;

import com.garveshtiwari.spiritual_app_backend.journal.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEntryRepository
        extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry>
    findByUserIdOrderByCreatedAtDesc(
            Long userId
    );
}
package com.garveshtiwari.spiritual_app_backend.journal.repository;

import com.garveshtiwari.spiritual_app_backend.journal.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JournalEntryRepository
        extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry>
    findByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    Optional<JournalEntry> findByIdAndUserId(
            Long id,
            Long userId
    );
}
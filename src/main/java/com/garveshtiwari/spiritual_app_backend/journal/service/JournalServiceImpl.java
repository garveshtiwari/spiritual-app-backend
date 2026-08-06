package com.garveshtiwari.spiritual_app_backend.journal.service;

import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.book.repository.VerseRepository;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend.journal.dto.JournalRequest;
import com.garveshtiwari.spiritual_app_backend.journal.dto.JournalResponse;
import com.garveshtiwari.spiritual_app_backend.journal.entity.JournalEntry;
import com.garveshtiwari.spiritual_app_backend.journal.mapper.JournalMapper;
import com.garveshtiwari.spiritual_app_backend.journal.repository.JournalEntryRepository;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl
        implements JournalService {

    private final JournalEntryRepository journalRepository;

    private final VerseRepository verseRepository;

    private final UserRepository userRepository;

    private final JournalMapper journalMapper;

    private User getCurrentUser() {

        String email = SecurityContextHolder
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

    private JournalEntry getJournalEntryByUser(
            Long journalId
    ) {

        User user = getCurrentUser();

        return journalRepository
                .findByIdAndUserId(
                        journalId,
                        user.getId()
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Journal entry not found."
                        )
                );
    }

    @Override
    public JournalResponse createJournalEntry(
            JournalRequest request
    ) {

        User user = getCurrentUser();

        Verse verse = null;

        if (request.getVerseId() != null) {

            verse = verseRepository
                    .findById(request.getVerseId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Verse not found."
                            )
                    );
        }

        JournalEntry journalEntry = JournalEntry
                .builder()
                .user(user)
                .verse(verse)
                .title(request.getTitle())
                .content(request.getContent())
                .mood(request.getMood())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        journalRepository.save(journalEntry);

        return journalMapper.toResponse(
                journalEntry
        );
    }

    @Override
    public List<JournalResponse> getJournalEntries() {

        User user = getCurrentUser();

        return journalRepository
                .findByUserIdOrderByCreatedAtDesc(
                        user.getId()
                )
                .stream()
                .map(journalMapper::toResponse)
                .toList();
    }

    @Override
    public JournalResponse getJournalEntry(
            Long journalId
    ) {

        JournalEntry journalEntry =
                getJournalEntryByUser(journalId);

        return journalMapper.toResponse(
                journalEntry
        );
    }

    @Override
    public JournalResponse updateJournalEntry(
            Long journalId,
            JournalRequest request
    ) {

        JournalEntry journalEntry =
                getJournalEntryByUser(journalId);

        Verse verse = null;

        if (request.getVerseId() != null) {

            verse = verseRepository
                    .findById(request.getVerseId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Verse not found."
                            )
                    );
        }

        journalEntry.setVerse(verse);
        journalEntry.setTitle(request.getTitle());
        journalEntry.setContent(request.getContent());
        journalEntry.setMood(request.getMood());
        journalEntry.setUpdatedAt(LocalDateTime.now());

        journalRepository.save(journalEntry);

        return journalMapper.toResponse(
                journalEntry
        );
    }

    @Override
    public void deleteJournalEntry(
            Long journalId
    ) {

        JournalEntry journalEntry =
                getJournalEntryByUser(journalId);

        journalRepository.delete(journalEntry);
    }
}
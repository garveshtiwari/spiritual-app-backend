package com.garveshtiwari.spiritual_app_backend.history.service;

import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import com.garveshtiwari.spiritual_app_backend.book.repository.VerseRepository;
import com.garveshtiwari.spiritual_app_backend.common.exception.ResourceNotFoundException;
import com.garveshtiwari.spiritual_app_backend.history.dto.ReadingHistoryRequest;
import com.garveshtiwari.spiritual_app_backend.history.dto.ReadingHistoryResponse;
import com.garveshtiwari.spiritual_app_backend.history.entity.ReadingHistory;
import com.garveshtiwari.spiritual_app_backend.history.mapper.ReadingHistoryMapper;
import com.garveshtiwari.spiritual_app_backend.history.repository.ReadingHistoryRepository;
import com.garveshtiwari.spiritual_app_backend.user.entity.User;
import com.garveshtiwari.spiritual_app_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingHistoryServiceImpl
        implements ReadingHistoryService {

    private final ReadingHistoryRepository historyRepository;

    private final VerseRepository verseRepository;

    private final UserRepository userRepository;

    private final ReadingHistoryMapper historyMapper;

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

    private ReadingHistory getHistoryEntryByUser(
            Long historyId
    ) {

        User user = getCurrentUser();

        return historyRepository
                .findByIdAndUserId(
                        historyId,
                        user.getId()
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "History entry not found."
                        )
                );
    }

    @Override
    public ReadingHistoryResponse createHistoryEntry(
            ReadingHistoryRequest request
    ) {

        User user = getCurrentUser();

        Verse verse = verseRepository
                .findById(request.getVerseId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Verse not found."
                        )
                );

        ReadingHistory history = ReadingHistory
                .builder()
                .user(user)
                .verse(verse)
                .openedAt(LocalDateTime.now())
                .durationInSeconds(
                        request.getDurationInSeconds()
                )
                .build();

        historyRepository.save(history);

        return historyMapper.toResponse(history);
    }

    @Override
    public List<ReadingHistoryResponse> getHistory() {

        User user = getCurrentUser();

        return historyRepository
                .findByUserIdOrderByOpenedAtDesc(
                        user.getId()
                )
                .stream()
                .map(historyMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteHistoryEntry(
            Long historyId
    ) {

        ReadingHistory history =
                getHistoryEntryByUser(historyId);

        historyRepository.delete(history);
    }
}
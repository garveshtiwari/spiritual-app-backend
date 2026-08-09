package com.garveshtiwari.spiritual_app_backend.preference.repository;

import com.garveshtiwari.spiritual_app_backend.preference.entity.UserPreferredBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPreferredBookRepository
        extends JpaRepository<UserPreferredBook, Long> {

    List<UserPreferredBook> findByUserPreferenceId(
            Long userPreferenceId
    );

    List<UserPreferredBook> findByUserPreferenceUserId(
            Long userId
    );

    Optional<UserPreferredBook>
    findByUserPreferenceIdAndBookId(
            Long userPreferenceId,
            Long bookId
    );

    boolean existsByUserPreferenceIdAndBookId(
            Long userPreferenceId,
            Long bookId
    );

    void deleteByUserPreferenceId(
            Long userPreferenceId
    );
}
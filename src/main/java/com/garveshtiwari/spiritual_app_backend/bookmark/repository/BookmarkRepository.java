package com.garveshtiwari.spiritual_app_backend.bookmark.repository;

import com.garveshtiwari.spiritual_app_backend.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository
        extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    List<Bookmark> findByUserIdAndVerseIdIn(
            Long userId,
            List<Long> verseIds
    );

    Optional<Bookmark> findByUserIdAndVerseId(
            Long userId,
            Long verseId
    );

    boolean existsByUserIdAndVerseId(
            Long userId,
            Long verseId
    );

    void deleteByUserIdAndVerseId(
            Long userId,
            Long verseId
    );
}
package com.garveshtiwari.spiritual_app_backend.book.repository;

import com.garveshtiwari.spiritual_app_backend.book.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository
        extends JpaRepository<Chapter, Long> {

    List<Chapter> findByBookIdOrderByChapterNumberAsc(
            Long bookId
    );

    Optional<Chapter> findByBookIdAndChapterNumber(
            Long bookId,
            Integer chapterNumber
    );

    boolean existsByBookIdAndChapterNumber(
            Long bookId,
            Integer chapterNumber
    );
}
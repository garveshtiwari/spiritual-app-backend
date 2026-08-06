package com.garveshtiwari.spiritual_app_backend.book.repository;

import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerseRepository
        extends JpaRepository<Verse, Long> {

    List<Verse> findByChapterIdOrderByVerseNumberAsc(
            Long chapterId
    );

    Optional<Verse> findByChapterIdAndVerseNumber(
            Long chapterId,
            Integer verseNumber
    );

    boolean existsByChapterIdAndVerseNumber(
            Long chapterId,
            Integer verseNumber
    );
}
package com.garveshtiwari.spiritual_app_backend.book.repository;

import com.garveshtiwari.spiritual_app_backend.book.entity.Book;
import com.garveshtiwari.spiritual_app_backend.common.enums.AppLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository
        extends JpaRepository<Book, Long> {

    Optional<Book> findBySlug(
            String slug
    );

    Optional<Book> findByName(
            String name
    );

    boolean existsBySlug(
            String slug
    );

    List<Book> findByLanguage(
            AppLanguage language
    );
}
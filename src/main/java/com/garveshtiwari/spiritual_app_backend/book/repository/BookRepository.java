package com.garveshtiwari.spiritual_app_backend.book.repository;

import com.garveshtiwari.spiritual_app_backend.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBySlug(
            String slug
    );

    Optional<Book> findByName(
            String name
    );

    boolean existsBySlug(
            String slug
    );
}
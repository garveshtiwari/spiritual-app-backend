package com.garveshtiwari.spiritual_app_backend.book.mapper;

import com.garveshtiwari.spiritual_app_backend.book.dto.BookResponse;
import com.garveshtiwari.spiritual_app_backend.book.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponse toResponse(
            Book book
    ) {

        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .slug(book.getSlug())
                .description(book.getDescription())
                .language(book.getLanguage())
                .author(book.getAuthor())
                .category(book.getCategory())
                .coverImageUrl(book.getCoverImageUrl())
                .build();
    }
}
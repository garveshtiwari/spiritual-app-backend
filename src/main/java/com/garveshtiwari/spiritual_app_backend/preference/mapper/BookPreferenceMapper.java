package com.garveshtiwari.spiritual_app_backend.preference.mapper;

import com.garveshtiwari.spiritual_app_backend.preference.dto.BookPreferenceResponse;
import com.garveshtiwari.spiritual_app_backend.preference.entity.UserPreferredBook;
import org.springframework.stereotype.Component;

@Component
public class BookPreferenceMapper {

    public BookPreferenceResponse toResponse(
            UserPreferredBook userPreferredBook
    ) {

        return BookPreferenceResponse.builder()
                .id(userPreferredBook.getId())
                .bookId(
                        userPreferredBook
                                .getBook()
                                .getId()
                )
                .bookName(
                        userPreferredBook
                                .getBook()
                                .getName()
                )
                .slug(
                        userPreferredBook
                                .getBook()
                                .getSlug()
                )
                .build();
    }
}
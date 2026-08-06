package com.garveshtiwari.spiritual_app_backend.book.mapper;

import com.garveshtiwari.spiritual_app_backend.book.dto.ChapterResponse;
import com.garveshtiwari.spiritual_app_backend.book.entity.Chapter;
import org.springframework.stereotype.Component;

@Component
public class ChapterMapper {

    public ChapterResponse toResponse(
            Chapter chapter
    ) {

        return ChapterResponse.builder()
                .id(chapter.getId())
                .chapterNumber(chapter.getChapterNumber())
                .title(chapter.getTitle())
                .summary(chapter.getSummary())
                .build();
    }
}
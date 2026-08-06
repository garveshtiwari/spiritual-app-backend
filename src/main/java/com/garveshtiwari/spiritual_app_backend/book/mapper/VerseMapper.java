package com.garveshtiwari.spiritual_app_backend.book.mapper;

import com.garveshtiwari.spiritual_app_backend.book.dto.VerseResponse;
import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;
import org.springframework.stereotype.Component;

@Component
public class VerseMapper {

    public VerseResponse toResponse(
            Verse verse
    ) {

        return VerseResponse.builder()
                .id(verse.getId())
                .verseNumber(verse.getVerseNumber())
                .originalText(verse.getOriginalText())
                .transliteration(verse.getTransliteration())
                .translation(verse.getTranslation())
                .explanation(verse.getExplanation())
                .audioUrl(verse.getAudioUrl())
                .build();
    }
}
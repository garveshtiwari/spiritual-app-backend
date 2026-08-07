package com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.builder;

import com.garveshtiwari.spiritual_app_backend.book.entity.Verse;

public interface MetadataBuilder {

    String buildForVerse(
            Verse verse
    );
}
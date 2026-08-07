package com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.service;
import com.garveshtiwari.spiritual_app_backend
        .intelligence.indexing.dto.IndexingResponse;

public interface IndexingService {

    IndexingResponse indexAllVerses();

    IndexingResponse indexVerse(
            Long verseId
    );

    void reindexAllVerses();
}
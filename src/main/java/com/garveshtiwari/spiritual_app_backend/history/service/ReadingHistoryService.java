package com.garveshtiwari.spiritual_app_backend.history.service;

import com.garveshtiwari.spiritual_app_backend.history.dto.ReadingHistoryRequest;
import com.garveshtiwari.spiritual_app_backend.history.dto.ReadingHistoryResponse;

import java.util.List;

public interface ReadingHistoryService {

    ReadingHistoryResponse createHistoryEntry(
            ReadingHistoryRequest request
    );

    List<ReadingHistoryResponse> getHistory();

    void deleteHistoryEntry(
            Long historyId
    );
}
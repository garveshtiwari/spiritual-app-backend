package com.garveshtiwari.spiritual_app_backend.history.controller;

import com.garveshtiwari.spiritual_app_backend.history.dto.ReadingHistoryRequest;
import com.garveshtiwari.spiritual_app_backend.history.dto.ReadingHistoryResponse;
import com.garveshtiwari.spiritual_app_backend.history.service.ReadingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class ReadingHistoryController {

    private final ReadingHistoryService historyService;

    @PostMapping
    public ReadingHistoryResponse createHistoryEntry(
            @RequestBody ReadingHistoryRequest request
    ) {

        return historyService.createHistoryEntry(
                request
        );
    }

    @GetMapping
    public List<ReadingHistoryResponse> getHistory() {

        return historyService.getHistory();
    }

    @DeleteMapping("/{historyId}")
    public void deleteHistoryEntry(
            @PathVariable Long historyId
    ) {

        historyService.deleteHistoryEntry(
                historyId
        );
    }
}